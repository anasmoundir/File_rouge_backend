package com.example.demo.model;
import jakarta.persistence.*;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

    private String title;
    private String description;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
