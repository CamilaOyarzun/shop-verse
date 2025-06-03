package com.technova.shopverse.controller;

import com.technova.shopverse.dto.CreateProductDTO;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = productService.getAllProductsService();
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return productService.getProductByIdService(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         try {
             productService.deleteProductService(id);
             return ResponseEntity.noContent().build();
         } catch (IllegalArgumentException e){
             return ResponseEntity.notFound().build();
         }
    }
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);

    }
}
