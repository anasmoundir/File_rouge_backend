package com.example.demo.service.serviceImplementation;

import com.example.demo.Mapper.CategoryMapper;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.CustomDependencyException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;


    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("TestCategory");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("TestCategoryDTO");
    }
    @Test
    void createCategory_Success() {
        when(categoryMapper.categoryDTOToCategory(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.createCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO.getName(), result.getName());
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).categoryDTOToCategory(any(CategoryDTO.class));
        verify(categoryMapper).categoryToCategoryDTO(any(Category.class));
    }

    @Test
    void getCategoryById_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(categoryDTO.getName(), result.getName());
        verify(categoryRepository).findById(anyLong());
        verify(categoryMapper).categoryToCategoryDTO(any(Category.class));
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1L));

        assertTrue(exception.getMessage().contains("Category not found with id:"));
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void deleteCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).delete(any(Category.class));
    }

    @Test
    void getAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryMapper.categoryToCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(categoryDTO.getName(), result.get(0).getName());
        verify(categoryRepository).findAll();
        verify(categoryMapper, times(1)).categoryToCategoryDTO(any(Category.class));
    }

    @Test
    void updateCategory_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1L, categoryDTO));
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
    @Test
    void createCategory_NullData_ThrowsException() {
        when(categoryMapper.categoryDTOToCategory(null)).thenThrow(new IllegalArgumentException("CategoryDTO cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));
    }

    @Test
    void deleteCategory_WithDependencies_ThrowsCustomException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        doThrow(new CustomDependencyException("Cannot delete category with existing subcategories")).when(categoryRepository).delete(any(Category.class));

        assertThrows(CustomDependencyException.class, () -> categoryService.deleteCategory(1L));
    }

    @Test
    void getAllCategories_EmptyList() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(categoryRepository).findAll();
    }

    @Test
    void updateCategory_WithNonExistingId() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.updateCategory(999L, categoryDTO);
        });

        String expectedMessage = "Category not found with id: 999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(categoryRepository).findById(999L);
    }

    @Test
    void deleteCategory_WithNonExistingId() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory(999L);
        });

        String expectedMessage = "Category not found with id: 999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(categoryRepository).findById(999L);
    }


}