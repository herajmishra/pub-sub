package com.heraj.sgx_pub_sub.service;

import com.heraj.sgx_pub_sub.event.TopicEvent;
import com.heraj.sgx_pub_sub.model.Message;
import com.heraj.sgx_pub_sub.model.Subscriber;
import com.heraj.sgx_pub_sub.model.Topic;
import com.heraj.sgx_pub_sub.repository.MessageRepository;
import com.heraj.sgx_pub_sub.repository.SubscriberRepository;
import com.heraj.sgx_pub_sub.repository.TopicRepository;
import com.heraj.sgx_pub_sub.util.MOPEEncryptionUtil;
import com.heraj.sgx_pub_sub.util.OPEEncryptionUtil;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BrokerService {

    private final ApplicationEventPublisher eventPublisher;

    public BrokerService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public void registerSubscriber(String topicName, String subscriberName) {
        Topic topic = topicRepository.findByNameCustom(topicName);
        if (topic == null) {
            topic = new Topic();
            topic.setName(topicName);
            topicRepository.save(topic);  // Save topic to database
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setName(subscriberName);
        subscriber.setTopic(topic);
        subscriberRepository.save(subscriber);  // Save subscriber to database
    }

    @Transactional
    public void publishMessage(String topicName, String messageContent, String encryptionType) {
        Topic topic = topicRepository.findByNameCustom(topicName);
        String encryptedMessage;
        if ("OPE".equalsIgnoreCase(encryptionType)) {
            encryptedMessage = OPEEncryptionUtil.encrypt(messageContent);
        } else if ("MOPE".equalsIgnoreCase(encryptionType)) {
            encryptedMessage = MOPEEncryptionUtil.encrypt(messageContent);
        } else {
            throw new RuntimeException("Invalid encryption"); // No encryption
        }


        Message savedMessage = null;
        if (topic != null) {
            Message message = new Message();
            message.setContent(encryptedMessage);
            message.setEncryptionType(encryptionType);
            message.setTopic(topic);
            savedMessage = messageRepository.save(message);  // Save message to database
        }

        if (savedMessage != null) {
            eventPublisher.publishEvent(new TopicEvent(this, topicName, savedMessage.getId(), encryptionType));
        }
        System.out.println("Message published to topic " + topicName);
    }
}
