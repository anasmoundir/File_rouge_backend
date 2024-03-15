package com.example.demo.dto;

import lombok.Data;

@Data

public class TeacherRegistrationResponse {
    public TeacherRegistrationResponse(String message) {
        this.message = message;
    }
    private Long id;
    private String username;
    private String email;
    private String message;
}
