package com.heraj.sgx_pub_sub.service;

import com.heraj.sgx_pub_sub.model.Message;
import com.heraj.sgx_pub_sub.model.Subscriber;
import com.heraj.sgx_pub_sub.model.Topic;
import com.heraj.sgx_pub_sub.repository.MessageRepository;
import com.heraj.sgx_pub_sub.repository.SubscriberRepository;
import com.heraj.sgx_pub_sub.repository.TopicRepository;
import com.heraj.sgx_pub_sub.util.MOPEEncryptionUtil;
import com.heraj.sgx_pub_sub.util.OPEEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    private final TopicRepository topicRepository;

    private final MessageRepository messageRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, TopicRepository topicRepository, MessageRepository messageRepository) {
        this.subscriberRepository = subscriberRepository;
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }

    // Subscribe a user to a topic
    public void subscribe(String subscriberName, String topicName) {
        // Check if the subscriber is already subscribed
        Topic topic = topicRepository.findByNameCustom(topicName);
        List<Subscriber> existingSubscribers = subscriberRepository.findByTopic(topic);
        boolean alreadySubscribed = existingSubscribers.stream()
                .anyMatch(subscriber -> subscriber.getName().equals(subscriberName));

        if (!alreadySubscribed) {
            Subscriber subscriber = new Subscriber();
            subscriber.setName(subscriberName);
            subscriber.setTopic(topic);
            subscriberRepository.save(subscriber);
            System.out.println("Subscriber " + subscriberName + " subscribed to topic " + topic);
        } else {
            System.out.println("Subscriber " + subscriberName + " is already subscribed to topic " + topic);
        }
    }

    // Unsubscribe a user from a topic
    public void unsubscribe(String topicName, String subscriberName) {
        Topic topic = topicRepository.findByNameCustom(topicName);
        List<Subscriber> subscribers = subscriberRepository.findByTopic(topic);
        subscribers.stream()
                .filter(subscriber -> subscriber.getName().equals(subscriberName))
                .findFirst()
                .ifPresent(subscriberRepository::delete);
        System.out.println("Subscriber " + subscriberName + " unsubscribed from topic " + topic);
    }

    // Get all subscribers for a topic
    public List<Subscriber> getSubscribersByTopic(String topicName) {
        Topic topic = topicRepository.findByNameCustom(topicName);
        return subscriberRepository.findByTopic(topic);
    }

    public void notifySubscribers(Topic topic, Long message, String encryptionType) {
        List<Subscriber> subscribers = subscriberRepository.findByTopic(topic);

        if (subscribers.isEmpty()) {
            System.out.println("No subscribers for topic " + topic);
            return;
        }

        for (Subscriber subscriber : subscribers) {
            // Decrypt the message for each subscriber
            Optional<Message> encryptedMessage = messageRepository.findById(message);
            if (encryptedMessage.isPresent()) {
                String decryptedMessage = decryptMessage(encryptedMessage.get().getContent(), encryptionType);
                System.out.println("Notifying subscriber " + subscriber.getName() +
                        " on topic " + topic + " with message: " + decryptedMessage);
            }

        }
    }

    private String decryptMessage(String encryptedMessage, String encryptionType) {
        String messageContent;
        if ("OPE".equalsIgnoreCase(encryptionType)) {
            messageContent = OPEEncryptionUtil.decrypt(encryptedMessage);
        } else if ("MOPE".equalsIgnoreCase(encryptionType)) {
            messageContent = MOPEEncryptionUtil.decrypt(encryptedMessage);
        } else {
            throw new RuntimeException("Invalid encryption type"); // No encryption
        }
        return messageContent;
    }

}
