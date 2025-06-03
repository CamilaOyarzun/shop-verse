package com.technova.shopverse.controller;

import com.technova.shopverse.dto.CreateProductDTO;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Obtener todos los productos",
            description = "Este endpoint devuelve una lista con todos los productos disponibles"
    )
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = productService.getAllProductsService();
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @Operation(
            summary = "Obtener producto individual",
            description = "Este endpoint devuelve un producto por su ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return productService.getProductByIdService(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Crear producto",
            description = "Este endpoint crea un producto."
    )
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductDTO newProduct){
        try {
            ProductDTO product = productService.createProductService(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Actualizar producto",
            description = "Este endpoint actualiza un producto, se obtiene con su ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long id, @RequestBody CreateProductDTO updatedProduct) {
        try {
            ProductDTO updated = productService.updateProductService(id, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Eliminar producto",
            description = "Este endpoint elimina un producto por su ID del sistema"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         try {
             productService.deleteProductService(id);
             return ResponseEntity.noContent().build();
         } catch (IllegalArgumentException e){
             return ResponseEntity.notFound().build();
         }
    }

    @Operation(
            summary = "Obtener todos los productos por categoria",
            description = "Este endpoint devuelve una lista con todos los productos por la categoria ingresada"
    )
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);

    }
}
