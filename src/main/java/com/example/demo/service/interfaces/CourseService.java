package com.example.demo.service.interfaces;

import com.example.demo.dto.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface CourseService {

    @Transactional
    CourseDTO createCourse(String title, Long subcategoryId, Long instructorId, String description, LocalDate startDate, LocalDate endDate, MultipartFile courseImage) throws FileUploadException;

    @Transactional(readOnly = true)
    CourseDTO getCourseById(Long id);

    @Transactional
    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    @Transactional
    void deleteCourse(Long id);

    @Transactional(readOnly = true)
    List<CourseDTO> getAllCourses();

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesBySubcategory(Long subcategoryId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesByInstructor(Long instructorId);

    @Transactional(readOnly = true)
    List<CourseDTO> getCoursesOfTheCurrentTeacher();

    @Transactional
    CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO);

    @Transactional
    CourseDTO addResourcesToLesson(Long courseId, Long lessonId, List<ResourcesDTO> resourceDTOs);

    List<CourseDTO> searchCourses(String title, Long categoryId, Long instructorId);
}
