package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean approved;
    private String educationalQualifications;
    private String teachingExperience;
    private String professionalExperience;
    private String teachingPhilosophy;
    @Column(name = "teacher_references")
    private String references;
    private String sampleLessonPlans;
    private String availability;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}

