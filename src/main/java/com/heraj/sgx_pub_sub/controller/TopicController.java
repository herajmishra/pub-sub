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

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic savedTopic = topicService.createTopic(topic);
        return new ResponseEntity<>(savedTopic, HttpStatus.CREATED);
    }

}

