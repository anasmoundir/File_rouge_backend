package com.example.demo.controller;

import com.example.demo.model.Subcategory;
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

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<List<Subcategory>> getAllSubcategories() {
        try {
            List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
            return ResponseEntity.ok().body(subcategories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long id) {
        try {
            Subcategory subcategory = subcategoryService.getSubcategoryById(id);
            return ResponseEntity.ok().body(subcategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        try {
            List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(categoryId);
            return ResponseEntity.ok().body(subcategories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
        try {
            Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategory> updateSubcategory(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        try {
            Subcategory updatedSubcategory = subcategoryService.updateSubcategory(id, subcategory);
            return ResponseEntity.ok().body(updatedSubcategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        try {
            subcategoryService.deleteSubcategory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
