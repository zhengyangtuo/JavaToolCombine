package org.example.service;

import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.mapper.CommentMapper;
import org.example.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    public int addComent(Comment comment);

    List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit);

    int findCommentCount(int entityType, int entityId);

    Comment findCommentById(int entityId);
}
