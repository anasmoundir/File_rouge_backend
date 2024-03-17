package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourcesDTO {
    private String title;
    private String description;
    private String url;
    private Long lessonId;
    private Long courseId;
}
