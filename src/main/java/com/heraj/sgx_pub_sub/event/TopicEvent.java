package com.heraj.sgx_pub_sub.event;

import org.springframework.context.ApplicationEvent;

import org.springframework.context.ApplicationEvent;

public class TopicEvent extends ApplicationEvent {

    private final String topic;
    private final Long message;
    private final String encryptionType;

    public TopicEvent(Object source, String topic, Long message, String encryptionType) {
        super(source);
        this.topic = topic;
        this.message = message;
        this.encryptionType = encryptionType;
    }

    public String getTopic() {
        return topic;
    }

    public Long getMessage() {
        return message;
    }

    public String getEncryptionType() {
        return encryptionType;
    }
}

