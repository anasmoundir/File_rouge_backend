package com.example.demo.dto;

import lombok.Data;

@Data
public class TeacherRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String educationalQualifications;
    private String teachingExperience;
    private String professionalExperience;
    private String teachingPhilosophy;
    private String references;
    private String sampleLessonPlans;
    private String availability;
}