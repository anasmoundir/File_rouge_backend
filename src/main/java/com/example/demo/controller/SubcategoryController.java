package com.example.demo.controller;

import com.example.demo.dto.SubcategoryDTO;
import com.example.demo.exception.SubcategoryNotFoundException;
import com.example.demo.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/subcategory")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories() {
        List<SubcategoryDTO> subcategories = subcategoryService.getAllSubcategories();
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategoryById(@PathVariable Long id) {
        SubcategoryDTO subcategory = subcategoryService.getSubcategoryById(id);
        return ResponseEntity.ok(subcategory);
    }

    @PostMapping
    public ResponseEntity<SubcategoryDTO> createSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
        SubcategoryDTO createdSubcategory = subcategoryService.createSubcategory(subcategoryDTO);
        return new ResponseEntity<>(createdSubcategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@PathVariable Long id, @RequestBody SubcategoryDTO subcategoryDTO) {
        SubcategoryDTO updatedSubcategory = subcategoryService.updateSubcategory(id, subcategoryDTO);
        return ResponseEntity.ok(updatedSubcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.ok().build();
    }
}
