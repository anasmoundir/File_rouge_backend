package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.CourseMapper;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.dto.ResourcesDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SubcategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class courseServiceImpl  implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    public courseServiceImpl(CourseRepository courseRepository,
                             CategoryRepository categoryRepository,
                             SubcategoryRepository subcategoryRepository,
                             UserRepository userRepository,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
    }


    @Override
     @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO, Long categoryId, Long subcategoryId, Long instructorId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + subcategoryId));

        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new UserNotFoundException("Instructor not found with id: " + instructorId));

        Course course = courseMapper.courseDTOToCourse(courseDTO);
        course.setCategory(category);
        course.setSubcategory(subcategory);
        course.setInstructor(instructor);
        return courseMapper.courseToCourseDTO(courseRepository.save(course));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        return courseMapper.courseToCourseDTO(course);
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());

        return courseMapper.courseToCourseDTO(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));

        List<Course> courses = courseRepository.findByCategory(category);
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesBySubcategory(Long subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + subcategoryId));

        List<Course> courses = courseRepository.findBySubcategory(subcategory);
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesByInstructor(Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new UserNotFoundException("Instructor not found with id: " + instructorId));

        List<Course> courses = courseRepository.findByInstructor(instructor);
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getInstructorOfCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        User instructor = course.getInstructor();
        return courseMapper.userToUserDTO(instructor);
    }

    @Override
    @Transactional
    public CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        Lesson lesson = courseMapper.lessonDTOToLesson(lessonDTO);
        lesson.setCourse(course);
        course.getLessons().add(lesson);

        return courseMapper.courseToCourseDTO(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseDTO addResourcesToLesson(Long courseId, Long lessonId, List<ResourcesDTO> resourceDTOs) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        Lesson lesson = course.getLessons().stream()
                .filter(l -> l.getLessonId().equals(lessonId))
                .findFirst()
                .orElse(null);
        if (lesson == null) {
            throw new LessonNotFoundException("Lesson not found with id: " + lessonId);
        }

        List<Resources> resources = resourceDTOs.stream()
                .map(resourceDTO -> {
                    Resources resource = courseMapper.resourceDTOToResource(resourceDTO);
                    resource.setLesson(lesson);
                    return resource;
                })
                .collect(Collectors.toList());

        lesson.getResources().addAll(resources);
        return courseMapper.courseToCourseDTO(courseRepository.save(course));
    }


}
