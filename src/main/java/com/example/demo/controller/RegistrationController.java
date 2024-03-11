package com.example.demo.controller;

import com.example.demo.Mapper.TeacherMapper;
import com.example.demo.Mapper.userMapper;
import com.example.demo.dto.UserTeacherRequest;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
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
    private final TeacherMapper teacherMapper;

    private final userMapper usermapper;

    @Autowired
    public RegistrationController(RegistrationService registrationService,
                                  TeacherMapper teacherMapper, userMapper usermapper) {
        this.registrationService = registrationService;
        this.teacherMapper = teacherMapper;
        this.usermapper = usermapper;
    }
    @PostMapping("/Teacher")
    public ResponseEntity<String> registerUserAndTeacher(@RequestBody UserTeacherRequest request) {
        try {
            User user = usermapper.signUpDTOToUser(request.getUser());
            if (request.getTeacher() != null) {
                Teacher teacher = teacherMapper.teacherDTOToTeacher(request.getTeacher());
                user.setTeacher(teacher);
            }
            registrationService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

}

