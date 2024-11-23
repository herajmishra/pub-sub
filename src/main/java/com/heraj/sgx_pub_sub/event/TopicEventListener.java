package com.heraj.sgx_pub_sub.event;

import com.heraj.sgx_pub_sub.model.Subscriber;
import com.heraj.sgx_pub_sub.model.Topic;
import com.heraj.sgx_pub_sub.service.SubscriberService;
import com.heraj.sgx_pub_sub.service.TopicService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class TopicEventListener {

    private final SubscriberService subscriberService;


    public TopicEventListener(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @EventListener
    public void handleTopicEvent(TopicEvent event) {
        String topicName = event.getTopic();
        Long message = event.getMessage();
        String encryptionType = event.getEncryptionType();

        // Notify all subscribers of the topic
        List<Subscriber> subscribers = subscriberService.getSubscribersByTopic(topicName);

        for (Subscriber subscriber : subscribers) {
            subscriberService.notifySubscribers(subscriber.getTopic(), message, encryptionType);
        }
    }
}

