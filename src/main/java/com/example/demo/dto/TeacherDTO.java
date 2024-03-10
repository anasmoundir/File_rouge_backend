package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO {
    private Long id;
    private boolean approved;
    private String educationalQualifications;
    private String teachingExperience;
    private String professionalExperience;
    private String teachingPhilosophy;
    private String references;
    private String sampleLessonPlans;
    private String availability;
    private Long userId;
}
