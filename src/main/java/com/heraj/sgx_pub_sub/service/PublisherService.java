package com.heraj.sgx_pub_sub.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    private BrokerService brokerService;

    public void publishMessage(String topic, String messageContent, String encryptionType) {
        brokerService.publishMessage(topic, messageContent, encryptionType);
    }
}



