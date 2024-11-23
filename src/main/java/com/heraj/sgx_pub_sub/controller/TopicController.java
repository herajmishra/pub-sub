package com.heraj.sgx_pub_sub.controller;

import com.heraj.sgx_pub_sub.model.Topic;
import com.heraj.sgx_pub_sub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // Endpoint to create a new topic
    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        // Save the topic through the service layer
        Topic savedTopic = topicService.createTopic(topic);
        return new ResponseEntity<>(savedTopic, HttpStatus.CREATED);
    }

    // You can add more endpoints for other operations (e.g., get, update, delete) if needed
}

