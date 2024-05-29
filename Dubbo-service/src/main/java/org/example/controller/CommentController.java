package org.example.controller;

import org.example.constant.CommunityConstant;
import org.example.constant.ReturnCode;
import org.example.pojo.*;
import org.example.service.CommentService;
import org.example.service.DiscussPostService;
import org.example.service.EventProducerService;
import org.example.util.HostHolder;
import org.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private EventProducerService eventProducerService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/add/{discussPostId}")
    public ResultType addComment(@PathVariable("discussPostId") int discussPostId, @RequestBody Comment comment){
        System.out.print(discussPostId + " " + comment);
        User user1 = hostHolder.getUser();
        comment.setUserId(user1.getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComent(comment);

        //触发评论事件(系统通知)
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityId(comment.getEntityId())
                .setEntityType(comment.getEntityType())
                .setData("postId",discussPostId);
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            DiscussPost target = discussPostService.findDiscussPostById(discussPostId);
            event.setEntityUserId(target.getUserId());
        }else if (comment.getEntityType() == ENTITY_TYPE_COMMENT){
            Comment c = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(c.getUserId());
        }
        eventProducerService.fireEvent(event);

        if (comment.getEntityType() == ENTITY_TYPE_POST){
            event = new Event()
                    .setTopic(TOPIC_PUBLIC)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducerService.fireEvent(event);
            String redisKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(redisKey,discussPostId);
        }

        return ResultType.success(ReturnCode.SUCCESS,null);
    }

}
