package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/setting")
    public ResultType getSettingData(){
        return null;
    }
    @PostMapping("/head/url")
    public ResultType updataHeadUrl(){
        return null;
    }
    @PostMapping("/password")
    public ResultType updatePassword(String oldPassword,String newPassword)
    {
        return null;
    }
    @GetMapping("/profile/{userId}")
    public ResultType getProfileData(@PathVariable("userId")int userId){
        return null;
    }
    @GetMapping("/discuss/{userId}")
    public ResultType getMyDiscussPost(@PathVariable("userId")int userId){
        return null;
    }
    @GetMapping("/comment/{userId}")
    public ResultType getMyComments(@PathVariable("userId")int userId){
        return null;
    }

}
