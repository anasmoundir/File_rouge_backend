package com.example.demo.service.interfaces;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO, Long categoryId, Long subcategoryId, Long instructorId);
    CourseDTO getCourseById(Long id);
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
    List<CourseDTO> getAllCourses();

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesByCategory(Long categoryId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesBySubcategory(Long subcategoryId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesByInstructor(Long instructorId);

    @Transactional(readOnly = true)
    UserDTO getInstructorOfCourse(Long courseId);

    @Transactional
    CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO);

    @Transactional
    CourseDTO addResourcesToLesson(Long courseId, Long lessonId, List<ResourcesDTO> resourceDTOs);

    List<CourseDTO> searchCourses(String title, Long categoryId, Long instructorId);
}
