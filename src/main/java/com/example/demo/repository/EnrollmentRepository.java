package com.example.demo.repository;

import com.example.demo.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    Enrollment save(Enrollment enrollment);
    Optional<Enrollment> findById(Long enrollmentId);

    void delete(Enrollment enrollment);

    @Query(value = "SELECT course_id FROM enrollments WHERE user_id = :userId", nativeQuery = true)
    List<Long> findCourseIdsByUserId(Long userId);
}
