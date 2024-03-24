package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ResourcesDTO {
    private String title;
    private String description;
    private String url;
    private Long lessonId;
    private Long courseId;
    private MultipartFile  file;
}
