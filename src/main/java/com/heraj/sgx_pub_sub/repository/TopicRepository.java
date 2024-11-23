package com.heraj.sgx_pub_sub.repository;

import com.heraj.sgx_pub_sub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE t.name = :name")
    Topic findByNameCustom(@Param("name") String name);
}
