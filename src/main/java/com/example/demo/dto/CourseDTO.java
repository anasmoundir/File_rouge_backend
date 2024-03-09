package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CourseDTO {
    private Long courseId;
    private String title;
    private UserDTO instructor;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private CategoryDTO category;
    private SubcategoryDTO subcategory;
    private List<ResourcesDTO> resources;
    private List<LessonDTO> lessons;
}
