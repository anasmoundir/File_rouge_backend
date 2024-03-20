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

    @Query("SELECT t FROM Teacher t JOIN FETCH t.user u WHERE u.id = :id")
    Optional<Teacher> findByUser(@Param("id") Long id);


    @Modifying
    @Query("UPDATE Teacher t SET t.approved = true WHERE t.id = :id")
    Teacher approveTeacher(@Param("id") Long id);
    Optional<Teacher> findById(Long id);

    @Query("SELECT t FROM Teacher t JOIN FETCH t.user u JOIN FETCH u.userRole WHERE u.userRole.roleName = 'TEACHER'")
    List<Teacher> findAll();


}
