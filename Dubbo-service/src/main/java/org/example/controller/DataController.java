package org.example.controller;

import org.example.constant.ReturnCode;
import org.example.pojo.ResultType;
import org.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class DataController {
    @Autowired
    private DataService dataService;
    @RequestMapping(value="/data",method={RequestMethod.GET,RequestMethod.POST})
    public ResultType getData(){
        return null;
    }
    @GetMapping("/data/uv")
    public ResultType getUV(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date end)
    {
        long l = dataService.caculateUv(start, end);
        return ResultType.success(ReturnCode.SUCCESS,l);
    }
    @GetMapping("/data/dau")
    public ResultType getDau(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")Date start,
                             @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")Date end){
        System.out.println(start);
        System.out.println(end);
        long l = dataService.caculateDau(start, end);
        return ResultType.success(ReturnCode.SUCCESS,l);
    }
}
