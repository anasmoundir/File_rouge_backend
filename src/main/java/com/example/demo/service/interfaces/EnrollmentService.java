package com.example.demo.service.interfaces;

import com.example.demo.dto.EnrollmentDTO;

public interface EnrollmentService {
    EnrollmentDTO enrollUser(Long userId, Long courseId);
    void cancelEnrollment(Long enrollmentId);
}
