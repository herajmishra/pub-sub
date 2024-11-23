package com.heraj.sgx_pub_sub.service;

import com.heraj.sgx_pub_sub.model.Topic;
import com.heraj.sgx_pub_sub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // Method to create a topic
    public Topic createTopic(Topic topic) {
        // You can perform any additional logic (e.g., validation) here
        return topicRepository.save(topic);
    }

    public Topic findByTopic(String topic) {
        // You can perform any additional logic (e.g., validation) here
        return topicRepository.findByNameCustom(topic);
    }
}

