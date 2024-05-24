package org.example.service;

import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.mapper.CommentMapper;
import org.example.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface CommentService {
    public int addComent(Comment comment);
}
