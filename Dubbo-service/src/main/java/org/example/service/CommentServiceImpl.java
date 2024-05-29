package org.example.service;

import org.example.constant.CommunityConstant;
import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.mapper.CommentMapper;
import org.example.pojo.Comment;
import org.example.pojo.User;
import org.example.util.HostHolder;
import org.example.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService, CommunityConstant {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SensitiveFilter filter;
    @Autowired
    private DiscussPostService discussPostService;
    @Override
    public int addComent(Comment comment){
        if (comment == null) {
            throw new BussinessException("评论数据为空", ReturnCode.BUSSINESS_ERROR);
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(filter.filter(comment.getContent()));

        int row = commentMapper.insertComment(comment);
        //更新文章评论数
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.findCommentCount(comment.getEntityType(),comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);
        }

        return row;
    }

    @Override
    public List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommunityByEntity(entityType,entityId,offset,limit);
    }

    @Override
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.findCommentCount(entityType,entityId);
    }

    @Override
    public Comment findCommentById(int entityId) {
        return commentMapper.findCommentById(entityId);
    }
}
