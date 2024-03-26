package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.Subcategory;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{


    @Query(value = "SELECT * FROM course WHERE instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByInstructor(User instructor);

    @Query(value = "SELECT * FROM course WHERE subcategory_id = :subcategoryId", nativeQuery = true)
    List<Course> findBySubcategory(Long subcategoryId);

    @Query(value = "SELECT * FROM course WHERE teacher_id = :instructorId", nativeQuery = true)
    List<Course> findByInstructor_Id(Long instructorId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND subcategory_id IN (SELECT id FROM subcategory WHERE category_id = :categoryId) AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByTitleContainingAndSubcategoryCategoryIdAndInstructorId(String title, Long categoryId, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title%", nativeQuery = true)
    List<Course> findByTitleContaining(String title);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByTitleContainingAndInstructor_Id(String title, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND subcategory_id = :categoryId", nativeQuery = true)
    List<Course> findByTitleContainingAndSubcategory_Id(String title, Long categoryId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND subcategory_id IN (SELECT id FROM subcategory WHERE category_id = :categoryId) AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByTitleContainingAndSubcategory_CategoryIdAndInstructor_Id(String title, Long categoryId, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE subcategory_id IN (SELECT id FROM subcategory WHERE category_id = :categoryId)", nativeQuery = true)
    List<Course> findBySubcategory_CategoryId(Long categoryId);


    @Query(value = "SELECT * FROM course WHERE course_id IN :enrolledCourseIds", nativeQuery = true)
    List<Course> findByIdIn(@Param("enrolledCourseIds") List<Long> enrolledCourseIds);
}
