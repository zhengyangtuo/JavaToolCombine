package org.example.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.constant.CommunityConstant;
import org.example.pojo.DiscussPost;
import org.example.pojo.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerServiceImpl implements EventConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumerServiceImpl.class);
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    @KafkaListener(topics = {CommunityConstant.TOPIC_PUBLIC})
    public void handlePublishMessage(ConsumerRecord record) {
        if (record == null || record.value() == null){
            logger.error("消息内容为空");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if (event == null){
            logger.error("消息格式错误");
            return;
        }
        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());

        elasticSearchService.saveDiscusspost(post);

    }
}
