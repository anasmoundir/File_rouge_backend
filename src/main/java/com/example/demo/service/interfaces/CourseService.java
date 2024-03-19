package com.example.demo.service.interfaces;

import com.example.demo.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {
    @Transactional
    CourseDTO createCourse(CourseDTO courseDTO, Long categoryId, Long subcategoryId, Long instructorId);

    @Transactional(readOnly = true)
    CourseDTO getCourseById(Long id);

    @Transactional
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    @Transactional
    void deleteCourse(Long id);

    @Transactional(readOnly = true)
    List<CourseDTO> getAllCourses();

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesByCategory(Long categoryId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesBySubcategory(Long subcategoryId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesByInstructor(Long instructorId);

    @Transactional
    CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO);

    @Transactional
    CourseDTO addResourcesToLesson(Long courseId, Long lessonId, List<ResourcesDTO> resourceDTOs);

    List<CourseDTO> searchCourses(String title, Long categoryId, Long instructorId);
}
