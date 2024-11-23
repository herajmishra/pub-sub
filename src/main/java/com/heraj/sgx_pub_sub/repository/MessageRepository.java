package com.heraj.sgx_pub_sub.repository;

import com.heraj.sgx_pub_sub.model.Message;
import com.heraj.sgx_pub_sub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTopic(Topic topic);
}


