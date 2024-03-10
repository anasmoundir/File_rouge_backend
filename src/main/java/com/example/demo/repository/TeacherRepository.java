package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


    @Modifying
    @Query("UPDATE Teacher t SET t.approved = true WHERE t.id = :id")
    Teacher approveTeacher(@Param("id") Long id);
    Optional<Teacher> findById(Long id);
    List<Teacher> findAll();

}
