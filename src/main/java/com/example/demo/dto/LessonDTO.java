package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class LessonDTO {
    private Long lessonId;
    private String title;
    private String content;
    private CourseDTO course;
    private List<ResourcesDTO> resources;
}
