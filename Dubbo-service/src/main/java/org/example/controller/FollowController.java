package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.*;


@RestController
public class FollowController {
    @PostMapping("/follow")
    public ResultType follow(int entityId,int entityType){
        return null;
    }
    @PutMapping("/unfollow")
    public ResultType unfollow(int entityId,int entityType){
        return null;
    }
    @GetMapping("/followees/{userId}")
    public ResultType getFollowees(@PathVariable("userId")int userId)
    {
        return null;
    }
    @GetMapping("/followers/{userId}")
    public ResultType getFollowers(@PathVariable("userId")int userId)
    {
        return null;
    }

}
