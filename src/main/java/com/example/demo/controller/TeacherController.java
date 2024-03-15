package com.example.demo.controller;

import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.TeacherRegistrationDTO;
import com.example.demo.dto.TeacherRegistrationResponse;
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


    @PostMapping("/register")
    public ResponseEntity<TeacherRegistrationResponse> registerTeacher(@RequestBody TeacherRegistrationDTO teacherDTO) {
        teacherService.registerTeacher(teacherDTO);
        TeacherRegistrationResponse response = new TeacherRegistrationResponse("Teacher registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<String> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
