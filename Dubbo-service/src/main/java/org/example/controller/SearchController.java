package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @GetMapping("/search")
    public ResultType search(String keyWord){
        return null;
    }
}
