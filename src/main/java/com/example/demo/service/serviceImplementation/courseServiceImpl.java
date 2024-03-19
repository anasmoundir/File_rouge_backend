package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.CourseMapper;
import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.*;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
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
    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public courseServiceImpl(CourseRepository courseRepository,
                             CategoryRepository categoryRepository,
                             SubcategoryRepository subcategoryRepository,
                             UserRepository userRepository,
                             CourseMapper courseMapper,
                             TeacherRepository teacherRepository,
                             TeacherMapper teacherMapper) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }


    @Override
    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Long subcategoryId = courseDTO.getSubcategoryId();
        Long instructorId = courseDTO.getInstructorId();

        Course course = courseMapper.courseDTOToCourse(courseDTO);
        course.setSubcategoryId(subcategoryId);
        course.setInstructorId(instructorId);

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
    public List<CourseDTO> getCoursesBySubcategory(Long subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + subcategoryId));

        List<Course> courses = courseRepository.findBySubcategory(subcategory.getId());
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


    @Override
    public List<CourseDTO> searchCourses(String title, Long categoryId, Long instructorId) {
        List<Course> courses;

        if (title != null && categoryId != null && instructorId != null) {
            courses = courseRepository.findByTitleContainingAndSubcategory_CategoryIdAndInstructor_Id(title, categoryId, instructorId);
        } else if (title != null && categoryId != null) {
            courses = courseRepository.findByTitleContainingAndSubcategory_Id(title, categoryId);
        } else if (title != null && instructorId != null) {
            courses = courseRepository.findByTitleContainingAndInstructor_Id(title, instructorId);
        } else if (categoryId != null && instructorId != null) {
            courses = courseRepository.findByInstructor_Id(instructorId);
        } else if (title != null) {
            courses = courseRepository.findByTitleContaining(title);
        } else if (categoryId != null) {
            courses = courseRepository.findBySubcategory_CategoryId(categoryId);
        } else {
            courses = courseRepository.findAll();
        }
        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }


}
