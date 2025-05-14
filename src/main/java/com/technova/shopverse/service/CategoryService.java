package com.technova.shopverse.service;

import com.technova.shopverse.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    List<Category> getAllCategoriesService();
    Optional<Category> getCategoryByIdService(Long id);
    Category createCategoryService(Category category);
    Category updateCategoryService(Long id, Category updated);
    void deleteCategoryService(Long id);
}
