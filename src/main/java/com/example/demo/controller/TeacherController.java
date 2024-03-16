package com.example.demo.controller;

import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.TeacherRegistrationDTO;
import com.example.demo.dto.TeacherRegistrationResponse;
import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.model.Teacher;
import com.example.demo.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherController(TeacherService teacherService,
                             TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teacherDTOs = teacherService.getAllTeachers().stream().map(teacherMapper::teacherToTeacherDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teacherDTOs);
    }

    @PutMapping("/{teacherId}/approve")
    public ResponseEntity<TeacherDTO> approveTeacher(@PathVariable Long teacherId) {
        TeacherDTO approvedTeacher = teacherService.approveTeacher(teacherId);
        return ResponseEntity.ok(approvedTeacher);
    }

}
