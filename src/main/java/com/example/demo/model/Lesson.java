package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
