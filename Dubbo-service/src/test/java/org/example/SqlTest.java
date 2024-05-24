package org.example;


import org.example.mapper.DiscussPostMapper;
import org.example.pojo.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class SqlTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void test1(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 1, 1, 1);
        System.out.println(discussPosts);
    }
}
