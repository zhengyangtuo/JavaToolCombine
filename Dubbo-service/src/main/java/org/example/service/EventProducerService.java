package org.example.service;

import com.alibaba.fastjson.JSONObject;
import org.example.pojo.Event;

public interface EventProducerService {
    public void fireEvent(Event event);
}
