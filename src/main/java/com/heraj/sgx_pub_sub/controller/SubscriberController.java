package com.heraj.sgx_pub_sub.controller;

import com.heraj.sgx_pub_sub.model.Subscriber;
import com.heraj.sgx_pub_sub.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String subscriberName, @RequestParam String topic) {
        subscriberService.subscribe(subscriberName, topic);
        return ResponseEntity.ok("Subscriber " + subscriberName + " subscribed to topic " + topic);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String subscriberName, @RequestParam String topic) {
        subscriberService.unsubscribe(subscriberName, topic);
        return ResponseEntity.ok("Subscriber " + subscriberName + " unsubscribed from topic " + topic);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<Subscriber>> getSubscribers(@RequestParam String topic) {
        List<Subscriber> subscribers = subscriberService.getSubscribersByTopic(topic);
        return ResponseEntity.ok(subscribers);
    }
}

