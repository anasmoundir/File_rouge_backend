package com.example.demo.service.interfaces;

import com.example.demo.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {

    TeacherDTO validateTeacher(Long teacherId, boolean approved);
    List<TeacherDTO> getAllTeachers();
}
