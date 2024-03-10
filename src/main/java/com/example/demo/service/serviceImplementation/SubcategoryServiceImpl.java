package com.example.demo.service.serviceImplementation;

import com.example.demo.exception.SubcategoryNotFoundException;
import com.example.demo.model.Subcategory;
import com.example.demo.repository.SubcategoryRepository;
import com.example.demo.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }


    @Override

    public Subcategory getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + id));
    }

    @Override
    public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }
    @Override
    public Subcategory updateSubcategory(Long id, Subcategory newSubcategory) {
        Subcategory existingSubcategory = getSubcategoryById(id);

        existingSubcategory.setName(newSubcategory.getName());
        existingSubcategory.setCategory(newSubcategory.getCategory());

        return subcategoryRepository.save(existingSubcategory);
    }

    @Override
    public void deleteSubcategory(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new SubcategoryNotFoundException("Subcategory not found with id: " + id);
        }
        subcategoryRepository.deleteById(id);
    }
}
