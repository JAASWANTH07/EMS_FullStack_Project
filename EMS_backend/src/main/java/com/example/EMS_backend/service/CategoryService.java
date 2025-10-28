package com.example.EMS_backend.service;

import com.example.EMS_backend.dao.CategoryDao;
import com.example.EMS_backend.dao.entity.CategoryEntity;
import com.example.EMS_backend.dto.CategoryDTO;
import com.example.EMS_backend.exception.NotFoundException;
import com.example.EMS_backend.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryMapper categoryMapper;

    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryDao.findAll();
        List<CategoryDTO> allCategories = new ArrayList<>();

        for (CategoryEntity entity : categoryEntities) {
            allCategories.add(categoryMapper.toDTO(entity));
        }
        return allCategories;
    }

    // Get category by ID
    public CategoryDTO getCategory(int categoryId) {
        CategoryEntity categoryEntity = categoryDao.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(categoryId, "Category"));

        return categoryMapper.toDTO(categoryEntity);
    }

    // Add a new category
    public CategoryDTO addCategory(CategoryDTO newCategory) {
        
        CategoryEntity categoryEntity = categoryMapper.toEntity(newCategory);
        CategoryEntity addedCategory = categoryDao.saveAndFlush(categoryEntity);
        return categoryMapper.toDTO(addedCategory);
    }

    // Update a category
    public CategoryDTO updateCategory(CategoryDTO editCategory) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(editCategory);
        CategoryEntity updatedCategory = categoryDao.save(categoryEntity);
        return categoryMapper.toDTO(updatedCategory);
    }

    // Delete a category
    public void deleteCategory(int categoryId) {
        categoryDao.deleteById(categoryId);
    }
}
