package org.example.controller;


import org.example.pojo.DiscussPost;
import org.example.pojo.Page;
import org.example.pojo.ResultType;
import org.example.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private DiscussPostService discussPostService;
    @GetMapping("/index")
    public ResultType getIndexData(Page page, int orderModel){
        System.out.println(page + " " +orderModel);
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit(),orderModel);
        return ResultType.success(200,discussPosts);
    }
    @GetMapping("/error")
    public ResultType getErrorData(){
        return ResultType.fail(500,null);
    }
    @GetMapping("/denied")
    public ResultType getDeniedData()
    {
        return ResultType.fail(404,null);
    }
}
