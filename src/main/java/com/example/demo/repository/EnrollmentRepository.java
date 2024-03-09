package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Optional<Object> findById(Long enrollmentId);

    void delete(Enrollment enrollment);
}
