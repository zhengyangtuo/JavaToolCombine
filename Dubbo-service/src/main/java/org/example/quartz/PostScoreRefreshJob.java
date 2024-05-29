package org.example.quartz;

import org.example.constant.CommunityConstant;
import org.example.constant.ReturnCode;
import org.example.exception.SystemException;
import org.example.pojo.DiscussPost;
import org.example.service.DiscussPostService;
import org.example.service.ElasticSearchService;
import org.example.util.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PostScoreRefreshJob implements Job, CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(PostScoreRefreshJob.class);
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private ElasticSearchService elasticSearchService;
    private static final Date epoch;

    static {
        try{
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-01-01 00:00:00");
        } catch (ParseException e) {
            throw new SystemException("初始化Epoch纪元失败", ReturnCode.FAIL);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);
        if (operations.size() == 0){
            logger.info("[任务取消] 没有需要刷新的帖子");
            return;
        }
        logger.info("[任务开始] 正在刷新帖子的分数，帖子数量:"+operations.size());
        while (operations.size() > 0){
            this.refresh((Integer) operations.pop());
        }
        logger.info("[任务结束] 帖子刷新完毕");
    }

    private void refresh(int postId) {
        DiscussPost discussPost = discussPostService.findDiscussPostById(postId);
        if (discussPost == null){
            logger.error("该帖子不存在:id="+postId);
            return;
        }
        //是否加精
        boolean wonderful = discussPost.getStatus() == 1;
        //评论数量
        int commentCount = discussPost.getCommentCount();
        //点赞数量
        //TODO(待补充)
        long likeCount = 0;
        //计算权重
        double w = (wonderful ? 75 :0 ) + commentCount * 10 + likeCount * 2;
        //分数=权重+发帖距离天数
        double score = Math.log10(Math.max(w,1)) + (discussPost.getCreateTime().getTime() - epoch.getTime()) / (1000 * 3600 * 24);
        discussPostService.updateScore(postId,score);
        discussPost.setScore(score);
        elasticSearchService.saveDiscusspost(discussPost);
    }
}
