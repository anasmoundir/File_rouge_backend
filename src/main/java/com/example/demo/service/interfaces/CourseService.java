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
    List<CourseDTO> getCoursesByCategory(Long categoryId);
    List<CourseDTO> getCoursesBySubcategory(Long subcategoryId);
    List<CourseDTO> getCoursesByInstructor(Long instructorId);
    UserDTO getInstructorOfCourse(Long courseId);
    CourseDTO addResourcesToLesson(Long courseId, Long lessonId, List<ResourcesDTO> resourceDTOs);

    @Transactional
    CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO);
}
