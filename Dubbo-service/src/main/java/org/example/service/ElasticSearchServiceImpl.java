package org.example.service;

import org.example.mapper.DiscussPostRepository;
import org.example.pojo.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private DiscussPostRepository discussPostRepository;
    @Override
    public void saveDiscusspost(DiscussPost post) {
        discussPostRepository.save(post);
    }
}
