package com.technova.shopverse.service.impl;

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

    public List<Category> getAllCategoriesService(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryByIdService(Long id){
        return categoryRepository.findById(id);
    }

    public Category createCategoryService(Category category){
        if(category.getName() == null || category.getName().isBlank()){
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacía.");
        }
        if(category.getDescription().length() < 10){
            throw new IllegalArgumentException("La descripción debe ser mas larga.");
        }
        return categoryRepository.save(category);
    }


    public Category updateCategoryService(Long id, Category updated){
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
        return categoryRepository.save(category);
    }


    public void deleteCategoryService(Long id) {
        categoryRepository.deleteById(id);
    }
}