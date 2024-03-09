package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean approved;
    private String educationalQualifications;
    private String teachingExperience;
    private String professionalExperience;
    private String teachingPhilosophy;
    private String references;
    private String sampleLessonPlans;
    private String availability;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}