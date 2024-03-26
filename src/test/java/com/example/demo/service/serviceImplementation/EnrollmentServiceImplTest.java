package com.example.demo.service.serviceImplementation;


import com.example.demo.Mapper.EnrollementMapper;
import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.exception.EnrollmentNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollementMapper enrollmentMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void enrollUser_Success() {
        Long courseId = 1L;
        String username = "user1";

        User user = new User();
        user.setUsername(username);

        Course course = new Course();
        course.setCourseId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(new Date());
        enrollment.setUser(user);
        enrollment.setCourse(course);

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setCourseId(courseId);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        when(enrollmentMapper.enrollmentToEnrollmentDTO(any(Enrollment.class))).thenReturn(enrollmentDTO);

        EnrollmentDTO result = enrollmentService.enrollUser(courseId);

        assertNotNull(result);
        assertEquals(courseId, result.getCourseId());
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(courseRepository).findById(courseId);
    }

    @Test
    void enrollUser_CourseNotFound() {
        Long courseId = 1L;
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user1");
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CourseNotFoundException.class, () -> enrollmentService.enrollUser(courseId));

        assertTrue(exception.getMessage().contains("Course not found with id:"));
        verify(courseRepository).findById(courseId);
    }

    @Test
    void cancelEnrollment_Success() {
        Long enrollmentId = 1L;
        Enrollment enrollment = new Enrollment();

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        doNothing().when(enrollmentRepository).delete(enrollment);

        enrollmentService.cancelEnrollment(enrollmentId);

        verify(enrollmentRepository).findById(enrollmentId);
        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void cancelEnrollment_NotFound() {
        Long enrollmentId = 1L;
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.cancelEnrollment(enrollmentId));

        assertTrue(exception.getMessage().contains("Enrollment not found with id:"));
        verify(enrollmentRepository).findById(enrollmentId);
    }



}