package com.example.demo.controller;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.exception.TeacherNotFoundException;
import com.example.demo.service.interfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/{teacherId}/validate")
    public ResponseEntity<TeacherDTO> validateTeacher(@PathVariable Long teacherId) {
        TeacherDTO validatedTeacher = teacherService.validateTeacher(teacherId, true);
        return ResponseEntity.ok(validatedTeacher);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<String> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
