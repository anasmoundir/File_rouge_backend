package com.example.demo.repository;

import com.example.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT * FROM lesson l INNER JOIN course c ON l.course_id = c.course_id WHERE c.category_id = ?1", nativeQuery = true)
    List<Lesson> findLessonsByCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM lesson l INNER JOIN course c ON l.course_id = c.course_id WHERE c.subcategory_id = ?1", nativeQuery = true)
    List<Lesson> findLessonsBySubcategoryId(Long subcategoryId);

    @Query(value = "SELECT * FROM lesson l INNER JOIN course c ON l.course_id = c.course_id INNER JOIN user u ON c.instructor_id = u.user_id WHERE u.user_id = ?1", nativeQuery = true)
    List<Lesson> findLessonsByInstructorUserId(Long userId);

    // the list of the lesson of the course
    @Query(value = "SELECT * FROM lesson WHERE course_id = ?1", nativeQuery = true)
    List<Lesson> findLessonsByCourseId(Long courseId);
}
