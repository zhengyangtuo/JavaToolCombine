package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @PostMapping("/like")
    public ResultType like(int entityType, int entityId, int entityUserId, int postId){
        return null;
    }
}
