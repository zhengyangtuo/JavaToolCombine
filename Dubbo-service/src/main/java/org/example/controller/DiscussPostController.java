package org.example.controller;


import org.example.constant.ReturnCode;
import org.example.exception.SystemException;
import org.example.pojo.ResultType;
import org.example.service.DiscussPostService;
import org.example.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/discuss")
@Validated
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Value("${community.path.editormdUploadPath}")
    private String editormdUploadPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/publish")
    public ResultType getPublishData(){
        return null;
    }
    @PostMapping("/uploadMdPic")
    public ResultType uploadMdPic(@RequestBody(required = false)MultipartFile file){
        String url = null;
        try{
            String name = file.getOriginalFilename();
            String suffix = name.substring(name.lastIndexOf('.'));
            String fileName = CommunityUtil.generateUUID() + suffix;

            File dest = new File(editormdUploadPath + "/" + fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            url = domain + contextPath + "/editor-md-upload/" + fileName;
        } catch (IOException e) {
            throw new SystemException(e.getMessage(),ReturnCode.FAIL);
        }
        return ResultType.success(ReturnCode.SUCCESS,url);
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
