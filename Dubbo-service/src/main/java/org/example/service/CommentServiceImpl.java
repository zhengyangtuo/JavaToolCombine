package org.example.service;

import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.mapper.CommentMapper;
import org.example.pojo.Comment;
import org.example.pojo.User;
import org.example.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public int addComent(Comment comment){
        if (comment == null) {
            throw new BussinessException("评论数据为空", ReturnCode.BUSSINESS_ERROR);
        }
        int row = commentMapper.insertComment(comment);
        return row;
    }
}
