package com.example.demo.service.interfaces;

import com.example.demo.dto.EnrollmentDTO;
import org.springframework.transaction.annotation.Transactional;

public interface EnrollmentService {
    @Transactional
    EnrollmentDTO enrollUser(Long courseId);

    void cancelEnrollment(Long enrollmentId);
}
