package com.technova.shopverse.controller;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.dto.CategoryDetailDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categorias", description = "Operaciones relacionadas con categorias")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Operation(
            summary = "Obtener todas las categorias",
            description = "Este endpoint devuelve una lista con todas las categorias disponibles"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.getAllCategoriesService();
        if(categories.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    @Operation(
            summary = "Obtener categoria por ID",
            description = "Este endpoint devuelve una categoria por su ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryByIdService(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Crear categoria",
            description = "Este endpoint crea una categoria en el sistema"
    )
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO newCategory){
            CategoryDTO category = categoryService.createCategoryService(newCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Actualizar categoria",
            description = "Este endpoint actualiza una categoria por su ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryDTO updatedCategory) {
        try {
            CategoryDTO updated = categoryService.updateCategoryService(id, updatedCategory);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();

        }
    }

    @Operation(
            summary = "Eliminar categoria",
            description = "Este endpoint elimina una categoria del sistema"
    )
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
    @Operation(
            summary = "Detalle de la categoria",
            description = "Este endpoint devuelve todo el detalle de una categoria y una lista de los nombres de los productos asociados"
    )
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
