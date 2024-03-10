package com.example.demo.repository;

import com.example.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse_Instructor_Id(Long instructorId);

    List<Lesson> findByCourse_Category_Id(Long categoryId);

    List<Lesson> findByCourse_Subcategory_Id(Long subcategoryId);

    List<Lesson> findByCourse_Instructor_User_UserId(Long studentId);
}
