package com.technova.shopverse.controller;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.dto.CategoryDetailDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.getAllCategoriesService();
        if(categories.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryByIdService(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO newCategory){
            CategoryDTO category = categoryService.createCategoryService(newCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryDTO updatedCategory) {
        try {
            CategoryDTO updated = categoryService.updateCategoryService(id, updatedCategory);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryService(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDetailDTO> getCategoryDetails(@PathVariable Long id) {
        try {
            CategoryDetailDTO dto = categoryService.getCategoryDTOById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
