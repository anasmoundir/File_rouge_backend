package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryDTO {
    private Long id;
    private String name;
    private CategoryDTO category;
}
