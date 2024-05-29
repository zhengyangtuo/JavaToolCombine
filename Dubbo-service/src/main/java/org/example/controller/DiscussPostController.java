package org.example.controller;


import net.bytebuddy.asm.Advice;
import org.example.constant.CommunityConstant;
import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.exception.SystemException;
import org.example.pojo.*;
import org.example.service.*;
import org.example.util.CommunityUtil;
import org.example.util.HostHolder;
import org.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.constant.CommunityConstant.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/discuss")
@Validated
public class DiscussPostController implements CommunityConstant{
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducerService eventProducerService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;


    @Value("${community.path.editormdUploadPath}")
    private String editormdUploadPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/publish")
    public ResultType getPublishData(){
        return null;
    }
    @PostMapping("/uploadMdPic")
    public ResultType uploadMdPic(@RequestBody(required = false)MultipartFile file){
        String url = null;
        try{
            String name = file.getOriginalFilename();
            String suffix = name.substring(name.lastIndexOf('.'));
            String fileName = CommunityUtil.generateUUID() + suffix;

            File dest = new File(editormdUploadPath + "/" + fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            url = domain + contextPath + "/editor-md-upload/" + fileName;
        } catch (IOException e) {
            throw new SystemException(e.getMessage(),ReturnCode.FAIL);
        }
        return ResultType.success(ReturnCode.SUCCESS,url);
    }
    @PostMapping("/add")
    public ResultType addDiscussPost(@NotEmpty(message="文章标题不能为空")String title,String content){
        System.out.println(title  + "\n" + content);
        User user = hostHolder.getUser();
        if (user == null){
            throw new SystemException("用户未登录",ReturnCode.USERNOTLOGIN);
        }
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setUserId(user.getId());
        discussPost.setCreateTime(new Date());
        int cur = discussPostService.addDiscussPost(discussPost);

        //将事件保存到消息队列中，再存入ES
        Event event = new Event()
                .setTopic(CommunityConstant.TOPIC_PUBLIC)
                .setUserId(user.getId())
                .setEntityId(CommunityConstant.ENTITY_TYPE_POST)
                .setEntityId(discussPost.getId());
        eventProducerService.fireEvent(event);

        //计算帖子分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey,discussPost.getId());

        return ResultType.success(ReturnCode.SUCCESS,null);
    }
    @PostMapping("/detail/{discussPostId}")
    public ResultType getDiscussPost(@PathVariable("discussPostId")int discussPostId,
                                     @RequestBody Page page){
        System.out.println(page);
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        if (discussPost == null){
            throw new BussinessException("文章不存在",ReturnCode.FAIL);
        }
        String content = HtmlUtils.htmlEscape(discussPost.getContent());
        discussPost.setContent(content);
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("discussPost",discussPost);
        //获取作者信息
        int userId = discussPost.getUserId();
        User author = userService.findUserById(userId);
        res.put("author",author);
        //获取点赞数目
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,discussPostId);
        res.put("likeCount",likeCount);
        //获取当前用户的点赞状态
        User user = hostHolder.getUser();
        int likeStatus = user == null ? 0 : likeService.findEntityLikeStatus(user.getId(),ENTITY_TYPE_POST,discussPostId);
        res.put("likeStatus",likeStatus);

        page.setLimit(5);
        page.setRows(discussPost.getCommentCount());
        //获取评论信息
        List<Comment> comments = commentService.findCommentByEntity(
            ENTITY_TYPE_POST,discussPost.getId(),page.getOffset(),page.getLimit()
        );
        List<HashMap<String,Object>> commentsInfo = new ArrayList<>();
        for (Comment comment : comments){
            HashMap<String,Object> info = new HashMap<>();
            info.put("comment",comment);
            info.put("author",comment.getUserId());
            Long count = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,comment.getId());
            info.put("likeCount",count);
            int status = user == null ? 0 : likeService.findEntityLikeStatus(userId,ENTITY_TYPE_COMMENT,comment.getId());
            info.put("likeStatus",status);

            //获取评论的评论
            List<Comment> cs = commentService.findCommentByEntity(
                    ENTITY_TYPE_COMMENT,comment.getId(),0,Integer.MAX_VALUE);
            List<Map<String,Object>> replyList = new ArrayList<>();
            for (Comment c : cs){
                Map<String,Object> reply = new HashMap<>();
                reply.put("reply",reply);
                reply.put("author",c.getUserId());
                Long cnt = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,c.getId());
                reply.put("likeCount",cnt);
                int s = user == null ? 0 : likeService.findEntityLikeStatus(userId,ENTITY_TYPE_COMMENT,c.getId());
                reply.put("likeStatus",s);
                replyList.add(reply);
            }
            info.put("replyList",replyList);
            int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT,comment.getId());
            info.put("replyCount",replyCount);
            commentsInfo.add(info);

        }
        res.put("comments",commentsInfo);
        return ResultType.success(ReturnCode.SUCCESS,res);
    }
    @PostMapping("/top")
    public ResultType updateTop(int id,int type){
        return null;
    }
    @PostMapping("/wonderful")
    public ResultType setWonderfule(int id){
        return null;
    }
    @DeleteMapping("/{discussPostId}")
    public ResultType delete(@PathVariable("discussPostId")int id){
        return null;
    }
}
