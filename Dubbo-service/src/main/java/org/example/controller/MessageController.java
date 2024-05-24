package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @GetMapping("/letter/list")
    public ResultType getLetterList(){
        return null;
    }
    @GetMapping("/letter/detail/{conversationId}")
    public ResultType getLetterDetail(@PathVariable("conversationId")String conversationId){
        return null;
    }
    @PostMapping("/letter/send")
    public ResultType sendLetter(String toName,String content){
        return null;
    }
    @GetMapping("/notice/list")
    public ResultType getNoticeList(){
        return null;
    }
    @GetMapping("/notice/detail/{topic}")
    public ResultType getNoticeDetail(@PathVariable("topic")String topic){
        return null;
    }


}
