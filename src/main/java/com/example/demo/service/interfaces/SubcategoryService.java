package com.example.demo.service.interfaces;

import com.example.demo.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<Subcategory> getAllSubcategories();

    Subcategory getSubcategoryById(Long id);

    List<Subcategory> getSubcategoriesByCategoryId(Long categoryId);

    Subcategory createSubcategory(Subcategory subcategory);

    Subcategory updateSubcategory(Long id, Subcategory newSubcategory);

    void deleteSubcategory(Long id);
}
