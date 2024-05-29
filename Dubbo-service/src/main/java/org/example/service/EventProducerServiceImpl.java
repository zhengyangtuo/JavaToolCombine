package org.example.service;

import com.alibaba.fastjson.JSONObject;
import org.example.pojo.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducerServiceImpl implements EventProducerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public void fireEvent(Event event){
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
