package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.Date;

@RestController
public class DataController {
    @RequestMapping(value="/data",method={RequestMethod.GET,RequestMethod.POST})
    public ResultType getData(){
        return null;
    }
    @RequestMapping("/data/uv")
    public ResultType getUV(@DateTimeFormat(pattern="yyyy-mm-dd") Date start,
                            @DateTimeFormat(pattern="yyyy-mm-dd") Date end)
    {
        return null;
    }
    @RequestMapping("/data/dau")
    public ResultType getDau(@DateTimeFormat(pattern="yyyy-mm-dd")Date start,
                             @DateTimeFormat(pattern="yyyy-mm-dd")Date end){
        return null;
    }
}
