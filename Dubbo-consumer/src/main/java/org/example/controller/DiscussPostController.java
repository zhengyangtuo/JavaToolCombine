package org.example.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.pojo.ResultType;
import org.example.service.DiscussPostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;
import javax.xml.transform.Result;

@RestController
@RequestMapping("/discuss")
@Validated
public class DiscussPostController {
    @DubboReference
    private DiscussPostService discussPostService;
    @GetMapping("/publish")
    public ResultType getPublishData(){
        return null;
    }
    @PostMapping("/uploadMdPic")
    public ResultType uploadMdPic(@RequestParam(value="editormd-image-file",required = false)MultipartFile file){
        return null;
    }
    @PostMapping("/add")
    public ResultType addDiscussPost(@NotEmpty(message="文章标题不能为空")String title,String Content){
        System.out.println("addDiscussPost");
        return null;
    }
    @PostMapping("/detail/{discussPostId}")
    public ResultType getDiscussPost(@PathVariable(value="discussPostId")int discussPostId){
        return null;
    }
    @PostMapping("/top")
    public ResultType updateTop(int id,int type){
        return null;
    }
    @PostMapping("/wonderful")
    public ResultType setWonderfule(int id){
        return null;
    }
    @DeleteMapping("/{discussPostId}")
    public ResultType delete(@PathVariable("discussPostId")int id){
        return null;
    }
}
