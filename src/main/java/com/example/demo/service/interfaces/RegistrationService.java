package com.example.demo.service.interfaces;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserTeacherRequest;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {


    @Transactional
    void register(User user);

    UserDTO registerUser(SignUpDTO signUpDTO, UserRole userRole);

    UserRole determineUserRole(TeacherDTO teacherDTO);

    void registerTeacher(TeacherDTO teacherDTO, UserDTO userDTO);
}
