package com.example.demo.controller;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/enrollement ")
public class EnrollementController {
    private final EnrollmentService enrollmentService;
    @Autowired
    public EnrollementController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @PostMapping
    public EnrollmentDTO enrollUser(@RequestParam Long userId, @RequestParam Long courseId) {
        return enrollmentService.enrollUser(userId, courseId);
    }
    @DeleteMapping("/{enrollmentId}")
    public void cancelEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.cancelEnrollment(enrollmentId);
    }
}
