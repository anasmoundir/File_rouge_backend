package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.CourseMapper;
import com.example.demo.Mapper.LessonMapper;
import com.example.demo.Mapper.ResourceMapper;
import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.CourseDTO;
import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import com.example.demo.service.interfaces.AzureBlobStorageService;
import com.example.demo.service.interfaces.CourseService;
import com.example.demo.service.serviceImplementation.courseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

//
//    @InjectMocks
//    private courseServiceImpl courseService;
//
//    @Mock
//    private CourseRepository courseRepository;
//    @Mock
//    private Course course;
//
//    @Mock
//    private AuthenticationFacade authenticationFacade;
//
//    @Mock
//    private AzureBlobStorageService azureBlobStorageService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//@Test
//public void testCreateCourse_Success() throws Exception {
//    String title = "Test Course";
//    Long subcategoryId = 1L;
//    Long instructorId = 2L;
//    String description = "Course Description";
//    LocalDate startDate = LocalDate.now();
//    LocalDate endDate = LocalDate.now().plusMonths(1);
//    MultipartFile courseImage = mock(MultipartFile.class);
//
//    when(authenticationFacade.getAuthentication()).thenReturn(mock(Authentication.class)); // Mock authentication
//    if (azureBlobStorageService != null) {
//        when(azureBlobStorageService.uploadFile(courseImage)).thenReturn("image-url");
//    }
//    Course course = new Course();
//    course.setTitle(title);
//    course.setSubcategoryId(subcategoryId);
//    course.setInstructorId(instructorId);
//    course.setDescription(description);
//    course.setStartDate(startDate);
//    course.setEndDate(endDate);
//    when(courseRepository.save(course)).thenReturn(course);
//
//    // Act
//    CourseDTO savedCourse = courseService.createCourse(title, subcategoryId, instructorId, description, startDate, endDate, courseImage);
//
//    // Assert
//    verify(courseRepository).save(course);
//    if (azureBlobStorageService != null) {
//        verify(azureBlobStorageService).uploadFile(courseImage);
//    }
//    assertEquals(title, savedCourse.getTitle());
//}
//    @Test
//    public void getCourseById_ExistingCourse() {
//        Long courseId = 1L;
//        Course mockCourse = new Course();
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//
//        CourseDTO resultCourseDTO = courseService.getCourseById(courseId);
//        assertNotNull(resultCourseDTO);
//    }
//
//    @Test
//    public void getCourseById_NonExistingCourse() {
//        Long courseId = 999L;
//        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
//
//        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
//    }
//
//    @Test
//    public void updateCourse_Success() {
//        Long courseId = 1L;
//        Course mockCourse = new Course();
//        when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//
//        CourseDTO inputCourseDTO = new CourseDTO();
//        inputCourseDTO.setTitle("Updated Test Course");
//        inputCourseDTO.setDescription("Updated Test Description");
//        inputCourseDTO.setStartDate(LocalDate.now());
//        inputCourseDTO.setEndDate(LocalDate.now().plusMonths(2));
//
//        Course updatedMockCourse = new Course();
//        when(courseRepository.save(any(Course.class))).thenReturn(updatedMockCourse);
//
//        CourseDTO updatedCourseDTO = courseService.updateCourse(courseId, inputCourseDTO);
//        assertNotNull(updatedCourseDTO);
//    }
}
