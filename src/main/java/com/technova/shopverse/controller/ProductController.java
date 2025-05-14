package com.technova.shopverse.controller;

import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    private List<Product> products = new ArrayList<>();

    public ProductController(){
        products.add(new Product(1L, "Laptop Lenovo", "Notebook 15 pulgadas", 850.0));
        products.add(new Product(2L, "Mouse Logitech", "Mouse inalámbrico", 25.5));
        products.add(new Product(3L, "Monitor Samsung", "Monitor 24 pulgadas", 199.99));
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProductsService();
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductByIdService(id).orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProductService(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProductService(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
         productService.deleteProductService(id);
    }
}
