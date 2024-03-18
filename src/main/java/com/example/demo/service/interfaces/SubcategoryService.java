package com.example.demo.service.interfaces;

import com.example.demo.dto.SubcategoryDTO;
import com.example.demo.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<SubcategoryDTO> getAllSubcategories();

    SubcategoryDTO getSubcategoryById(Long id);

    List<SubcategoryDTO> getSubcategoriesByCategoryId(Long categoryId);

    SubcategoryDTO createSubcategory(SubcategoryDTO subcategoryDTO);

    SubcategoryDTO updateSubcategory(Long id, SubcategoryDTO subcategoryDTO);

    void deleteSubcategory(Long id);
}
