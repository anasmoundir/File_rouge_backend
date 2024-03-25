package com.example.demo.controller;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.interfaces.CourseService;
import com.example.demo.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/enrollement")
public class EnrollementController {
    private final EnrollmentService enrollmentService;

    private final CourseService courseService;

    @Autowired
    public EnrollementController(EnrollmentService enrollmentService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enrollUser(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            EnrollmentDTO resultDTO = enrollmentService.enrollUser(enrollmentDTO.getCourseId());
            return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long enrollmentId)
    {
        try {
            enrollmentService.cancelEnrollment(enrollmentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/courses")
    public ResponseEntity<List<CourseDTO>> getEnrolledCoursesByUserId() {
        try {
            List<CourseDTO> enrolledCourses = courseService.getEnrolledCoursesByUserId();
            return ResponseEntity.ok(enrolledCourses);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}