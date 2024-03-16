package com.example.demo.service.interfaces;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.TeacherRegistrationDTO;
import com.example.demo.model.Teacher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherService {
    @Transactional
    TeacherDTO approveTeacher(Long teacherId);

    List<Teacher> getAllTeachers();

    @Transactional
    void registerTeacher(TeacherRegistrationDTO registrationDTO);
}

