package com.example.demo.Mapper;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.SubcategoryDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Subcategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    SubcategoryDTO subcategoryToSubcategoryDTO(Subcategory subcategory);

    Subcategory subcategoryDTOToSubcategory(SubcategoryDTO subcategoryDTO);
}
