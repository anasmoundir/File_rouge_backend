package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Column(name = "subcategory_id", nullable = false)
    private Long subcategoryId;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resources> resources;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    public Teacher getInstructor() {
        return teacher;
    }
}