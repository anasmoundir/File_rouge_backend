package com.example.demo.service.interfaces;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.TeacherDTO;
import com.example.demo.dto.UserDTO;

public interface RegistrationService {
    void register(UserDTO userDTO, TeacherDTO teacherDTO);

    Long registerUser(UserDTO userDTO);

    Long registerUser(SignUpDTO signUpDTO);

    void registerTeacher(TeacherDTO teacherDTO, Long userId);
}
