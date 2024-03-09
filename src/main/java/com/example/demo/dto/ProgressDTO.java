package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressDTO {
    private Long userId;
    private Long courseId;
    private double percentage;
}
