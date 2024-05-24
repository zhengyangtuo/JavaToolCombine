package org.example.service;

import org.example.mapper.DiscussPostMapper;
import org.example.pojo.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImp implements DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit,int orderMode){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(userId,offset,limit,orderMode);
        return discussPosts;
    }
}
