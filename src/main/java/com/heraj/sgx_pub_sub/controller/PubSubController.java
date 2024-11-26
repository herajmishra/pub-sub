package com.heraj.sgx_pub_sub.controller;

import com.heraj.sgx_pub_sub.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pubsub")
public class PubSubController {

    @Autowired
    private BrokerService brokerService;

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam String topic, @RequestParam String subscriberName) {
        brokerService.registerSubscriber(topic, subscriberName); // Register to a specific topic
    }

    @PostMapping("/publish")
    public void publish(@RequestParam String topic, @RequestParam String message, @RequestParam String encryptionType) {
        brokerService.publishMessage(topic, message, encryptionType);
    }
}




