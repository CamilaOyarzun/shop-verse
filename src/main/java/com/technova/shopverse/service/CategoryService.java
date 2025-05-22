package com.technova.shopverse.service;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.dto.CategoryDetailDTO;
import com.technova.shopverse.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategoriesService();
    Optional<CategoryDTO> getCategoryByIdService(Long id);
    CategoryDTO createCategoryService(CategoryDTO category);
    CategoryDTO updateCategoryService(Long id, CategoryDTO updated);
    void deleteCategoryService(Long id);
    CategoryDetailDTO getCategoryDTOById(Long id);
}
