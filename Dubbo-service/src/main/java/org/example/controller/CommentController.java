package org.example.controller;

import org.example.constant.ReturnCode;
import org.example.pojo.Comment;
import org.example.pojo.ResultType;
import org.example.pojo.User;
import org.example.service.CommentService;
import org.example.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{discussPostId}")
    public ResultType addComment(@PathVariable("discussPostId") int discussPostId, @RequestBody Comment comment){
        System.out.print(discussPostId + " " + comment);
        User user1 = hostHolder.getUser();
        comment.setUserId(user1.getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComent(comment);
        return ResultType.success(ReturnCode.SUCCESS,null);
    }

}
