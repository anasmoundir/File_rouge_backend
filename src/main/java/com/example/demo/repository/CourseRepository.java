package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.Subcategory;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByCategory(Category category);

    List<Course> findByInstructor(User instructor);

    @Query(value = "SELECT * FROM course WHERE subcategory_id = :subcategoryId", nativeQuery = true)
    List<Course> findBySubcategory(Long subcategoryId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND category_id = :categoryId AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByTitleContainingAndCategoryIdAndInstructorId(String title, Long categoryId, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND category_id = :categoryId", nativeQuery = true)
    List<Course> findByTitleContainingAndCategoryId(String title, Long categoryId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title% AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByTitleContainingAndInstructorId(String title, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE category_id = :categoryId AND instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByCategoryIdAndInstructorId(Long categoryId, Long instructorId);

    @Query(value = "SELECT * FROM course WHERE title LIKE %:title%", nativeQuery = true)
    List<Course> findByTitleContaining(String title);

    @Query(value = "SELECT * FROM course WHERE category_id = :categoryId", nativeQuery = true)
    List<Course> findByCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM course WHERE instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByInstructorId(Long instructorId);
}
