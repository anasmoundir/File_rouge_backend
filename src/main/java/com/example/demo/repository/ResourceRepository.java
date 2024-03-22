package com.example.demo.repository;

import com.example.demo.model.Lesson;
import com.example.demo.model.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
    List<Resources> findByLesson(Lesson lesson);
}
