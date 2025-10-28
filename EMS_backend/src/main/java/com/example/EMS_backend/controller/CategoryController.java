package com.example.EMS_backend.controller;

import com.example.EMS_backend.dto.CategoryDTO;
import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.service.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/ems/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable("id") int categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @PostMapping("/add")
    public CategoryDTO addCategory(@RequestBody CategoryDTO newCategory) {
        return categoryService.addCategory(newCategory);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @PutMapping("/update")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO editCategory) {
        return categoryService.updateCategory(editCategory);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable("id") int categoryId) {
        categoryService.deleteCategory(categoryId);
    }

   
}
