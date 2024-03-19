package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.SubcategoryMapper;
import com.example.demo.dto.SubcategoryDTO;
import com.example.demo.exception.SubcategoryNotFoundException;
import com.example.demo.model.Subcategory;
import com.example.demo.repository.SubcategoryRepository;
import com.example.demo.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    @Override
    public List<SubcategoryDTO> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();

        return subcategories.stream()
                .map(subcategoryMapper::subcategoryToSubcategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDTO getSubcategoryById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + id));
        return subcategoryMapper.subcategoryToSubcategoryDTO(subcategory);
    }

    @Override
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(Long categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        return subcategories.stream()
                .map(subcategoryMapper::subcategoryToSubcategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDTO createSubcategory(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = subcategoryMapper.subcategoryDTOToSubcategory(subcategoryDTO);
        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);
        return subcategoryMapper.subcategoryToSubcategoryDTO(savedSubcategory);
    }

    @Override
    public SubcategoryDTO updateSubcategory(Long id, SubcategoryDTO newSubcategoryDTO) {
        Subcategory existingSubcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: " + id));

        existingSubcategory.setName(newSubcategoryDTO.getName());

        Subcategory updatedSubcategory = subcategoryRepository.save(existingSubcategory);
        return subcategoryMapper.subcategoryToSubcategoryDTO(updatedSubcategory);
    }

    @Override
    public void deleteSubcategory(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new SubcategoryNotFoundException("Subcategory not found with id: " + id);
        }
        subcategoryRepository.deleteById(id);
    }
}
