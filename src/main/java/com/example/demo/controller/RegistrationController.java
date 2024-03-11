package com.example.demo.controller;

import com.example.demo.dto.UserTeacherRequest;
import com.example.demo.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {
    private  final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    @PostMapping
    public ResponseEntity<String> registerUserAndTeacher(@RequestBody UserTeacherRequest request) {
        try {
            registrationService.register(request.getUser(), request.getTeacher());
            return ResponseEntity.status(HttpStatus.CREATED).body("Teacher registered successfully." );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user and teacher: " + e.getMessage());
        }
    }


}

