package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.Subcategory;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByCategory(Category category);

    List<Course> findByInstructor(User instructor);

    List<Course> findBySubcategory(Subcategory subcategory);
}
