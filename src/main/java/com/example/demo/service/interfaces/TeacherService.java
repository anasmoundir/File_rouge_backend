package com.example.demo.service.interfaces;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.TeacherRegistrationDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherService {

    TeacherDTO validateTeacher(Long teacherId, boolean approved);
    List<TeacherDTO> getAllTeachers();

    @Transactional
    void registerTeacher(TeacherRegistrationDTO registrationDTO);
}
