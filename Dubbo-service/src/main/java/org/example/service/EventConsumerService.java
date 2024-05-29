package org.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface EventConsumerService {
    public void handlePublishMessage(ConsumerRecord record);
}
