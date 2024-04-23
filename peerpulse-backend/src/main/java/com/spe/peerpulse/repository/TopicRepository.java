package com.spe.peerpulse.repository;

import com.spe.peerpulse.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByClassObj_ClassId(Long classId);
}