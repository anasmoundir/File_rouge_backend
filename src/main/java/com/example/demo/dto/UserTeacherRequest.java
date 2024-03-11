package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTeacherRequest {
    private SignUpDTO user;
    private TeacherDTO teacher;
}
