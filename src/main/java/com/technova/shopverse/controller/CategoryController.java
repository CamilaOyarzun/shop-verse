package com.technova.shopverse.controller;

import com.technova.shopverse.model.Category;
import com.technova.shopverse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    private List<Category> categories = new ArrayList<>();
    public CategoryController(){
        categories.add(new Category(1L, "Tecnología", "Productos electrónicos y computación"));
        categories.add(new Category(2L, "Hogar", "Artículos para el hogar y decoración"));
        categories.add(new Category(3L, "Ropa", "Indumentaria y accesorios"));
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategoriesService();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryByIdService(id).orElse(null);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategoryService(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategoryService(id, updatedCategory);

    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryService(id);
    }
}
