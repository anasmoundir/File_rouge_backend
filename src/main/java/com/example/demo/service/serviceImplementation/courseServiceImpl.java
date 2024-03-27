package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.CourseMapper;
import com.example.demo.Mapper.LessonMapper;
import com.example.demo.Mapper.ResourceMapper;
import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.*;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.interfaces.CourseService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class courseServiceImpl  implements CourseService {
    @Autowired
    private  AzureBlobStorageService azureBlobStorageService;
    private final AuthenticationFacade authenticationFacade;
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    private  final ResourceMapper ressourceMapper;

    private  final LessonMapper lessonMapper;



    private final EnrollmentRepository enrollmentRepository;

    public courseServiceImpl(CourseRepository courseRepository,
                             CategoryRepository categoryRepository,
                             SubcategoryRepository subcategoryRepository,
                             UserRepository userRepository,
                             CourseMapper courseMapper,
                             TeacherRepository teacherRepository,
                             TeacherMapper teacherMapper,
                             LessonMapper lessonMapper,
                             ResourceMapper ressourceMapper,
                             EnrollmentRepository enrollmentRepository,
                             AuthenticationFacade authenticationFacade) {
        this.courseRepository = courseRepository;
        this.authenticationFacade = authenticationFacade;
        this.ressourceMapper = ressourceMapper;
        this.lessonMapper = lessonMapper;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.enrollmentRepository = enrollmentRepository;
    }


    @Override
    @Transactional
    public CourseDTO createCourse(String title, Long subcategoryId, Long instructorId, String description, LocalDate startDate, LocalDate endDate, MultipartFile courseImage) throws FileUploadException {

        String imageUrl;
        try {
            imageUrl = azureBlobStorageService.uploadFile(courseImage);
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload image file: " + e.getMessage());
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle(title);
        courseDTO.setSubcategoryId(subcategoryId);
        courseDTO.setInstructorId(instructorId);
        courseDTO.setDescription(description);
        courseDTO.setStartDate(startDate);
        courseDTO.setEndDate(endDate);
        courseDTO.setImageUrl(imageUrl);

        CourseDTO savedCourse = savecourse(courseDTO);

        return savedCourse;
    }

    private CourseDTO savecourse(CourseDTO courseDTO) {
        Course course = courseMapper.courseDTOToCourse(courseDTO);
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Teacher teacher = teacherRepository.findByUser(user.getUserId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found for user with username: " + username));

        course.setTeacher(teacher);

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
    @Transactional(readOnly = true)
    public List<CourseDTO> getCoursesOfTheCurrentTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Teacher teacher = teacherRepository.findByUser(user.getUserId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found for user with username: " + username));

        List<Course> courses = courseRepository.findByInstructor_Id(teacher.getId());

        System.out.println("the number of the course :" + courses.size());

        return courses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);
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
                    Resources resource = ressourceMapper.resourceDTOToResource(resourceDTO);
                    resource.setLesson(lesson);
                    return resource;
                })
                .collect(Collectors.toList());

        lesson.getResources().addAll(resources);
        return courseMapper.courseToCourseDTO(courseRepository.save(course));
    }




    @Override
    @Transactional(readOnly = true)
    public List<CourseDTO> getEnrolledCoursesByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<Long> enrolledCourseIds = enrollmentRepository.findCourseIdsByUserId(user.getUserId());
        List<Course> enrolledCourses = courseRepository.findByIdIn(enrolledCourseIds);
        return enrolledCourses.stream()
                .map(courseMapper::courseToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> searchCoursesByTitleOrInstructorName(String searchTerm) {
        try {
            List<Course> coursesByTitle = courseRepository.findByTitleContaining(searchTerm);
            List<Course> coursesByInstructor = courseRepository.findAllByTeacher_User_UsernameLikeIgnoreCase(searchTerm);

            List<CourseDTO> courseDTOs = coursesByTitle.stream()
                    .map(courseMapper::courseToCourseDTO)
                    .collect(Collectors.toList());

            courseDTOs.addAll(coursesByInstructor.stream()
                    .map(courseMapper::courseToCourseDTO)
                    .collect(Collectors.toList()));

            return courseDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while searching for courses by title or instructor name", e);
        }
    }

}
