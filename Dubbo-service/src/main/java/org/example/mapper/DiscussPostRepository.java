package org.example.mapper;

import org.example.pojo.DiscussPost;
import org.springframework.data.elasticsearch.config.ElasticsearchNamespaceHandler;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

}
