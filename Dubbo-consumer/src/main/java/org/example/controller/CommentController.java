package org.example.controller;

import org.example.pojo.Comment;
import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @PostMapping("/add/{discussPostId}")
    public ResultType addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        return null;
    }

}
