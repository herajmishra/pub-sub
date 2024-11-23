package com.heraj.sgx_pub_sub.repository;

import com.heraj.sgx_pub_sub.model.Subscriber;
import com.heraj.sgx_pub_sub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findByTopic(Topic topic); // Find subscribers by topic
}


