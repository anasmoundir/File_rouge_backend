package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserTeacherRequest {
    private UserDTO user;
    private TeacherDTO teacher;
}
