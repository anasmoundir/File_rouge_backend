package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CourseDTO {
    private Long courseId;
    private String title;
    private Long subcategoryId;
    private Long instructorId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
}
