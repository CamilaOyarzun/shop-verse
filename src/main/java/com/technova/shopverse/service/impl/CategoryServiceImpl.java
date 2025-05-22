package com.technova.shopverse.service.impl;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.dto.CategoryDetailDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.repository.CategoryRepository;
import com.technova.shopverse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategoriesService(){
        return categoryRepository.findAll().stream() .map(this::toDTO).toList();
    }

    public Optional<CategoryDTO> getCategoryByIdService(Long id){
        return categoryRepository.findById(id).map(this::toDTO);
    }

    public CategoryDTO createCategoryService(CategoryDTO category){
        if(category.getName() == null || category.getName().isBlank()){
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacía.");
        }
        if(category.getDescription() == null || category.getDescription().length() < 10){
            throw new IllegalArgumentException("La descripción debe ser mas larga.");
        }
        Category newCategory = categoryRepository.save(new Category(category.getName(), category.getDescription()));
        return toDTO(newCategory);
    }


    public CategoryDTO updateCategoryService(Long id, CategoryDTO updated){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new IllegalArgumentException("La categoría que se intenta actualizar no existe.");
        }
        if(updated.getName() == null || updated.getName().isBlank()){
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacía.");
        }
        if(updated.getDescription().length() < 10){
            throw new IllegalArgumentException("La descripción debe ser mas larga.");
        }
        Category category = optionalCategory.get();
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        Category newCategory = categoryRepository.save(category);
        return toDTO(newCategory);
    }


    public void deleteCategoryService(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDetailDTO getCategoryDTOById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        List<String> productNames = category.getProducts().stream()
                .map(product -> product.getName())
                .toList();

        return new CategoryDetailDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productNames
        );
    }

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(),
                category.getName(),
                category.getDescription());
    }
}