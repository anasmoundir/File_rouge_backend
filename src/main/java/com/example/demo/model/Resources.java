    package com.example.demo.model;
    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Data
    public class Resources {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long resourceId;

        @Column(nullable = false)
        private String title;

        private String description;

        @Column(nullable = false)
        private String url;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "course_id")
        private Course course;

        private String s3Url;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "lesson_id")
        private Lesson lesson;
    }
