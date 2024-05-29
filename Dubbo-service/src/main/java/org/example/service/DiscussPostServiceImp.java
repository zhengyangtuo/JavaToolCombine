package org.example.service;

import org.example.constant.ReturnCode;
import org.example.exception.SystemException;
import org.example.mapper.DiscussPostMapper;
import org.example.pojo.DiscussPost;
import org.example.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostServiceImp implements DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter filter;
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit,int orderMode){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(userId,offset,limit,orderMode);
        return discussPosts;
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null){
            throw new SystemException("上送参数为空", ReturnCode.FAIL);
        }
        //转义html,防止注入攻击
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词汇
        discussPost.setTitle(filter.filter(discussPost.getTitle()));
        discussPost.setContent(filter.filter(discussPost.getContent()));
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    @Override
    public DiscussPost findDiscussPostById(int entityId) {
        return discussPostMapper.selectDiscussPostById(entityId);
    }

    @Override
    public int updateScore(int id, double score) {
        return discussPostMapper.updateScore(id, score);
    }

    @Override
    public void updateCommentCount(int entityId, int count) {
        discussPostMapper.updateCommentCount(entityId,count);
    }
}
