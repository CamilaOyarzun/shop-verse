package com.technova.shopverse.controller;

import com.technova.shopverse.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController(){
        products.add(new Product(1L, "Laptop Lenovo", "Notebook 15 pulgadas", 850.0));
        products.add(new Product(2L, "Mouse Logitech", "Mouse inalámbrico", 25.5));
        products.add(new Product(3L, "Monitor Samsung", "Monitor 24 pulgadas", 199.99));
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return products;
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setName(updatedProduct.getName());
                p.setDescription(updatedProduct.getDescription());
                p.setPrice(updatedProduct.getPrice());
                return p;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean removed = products.removeIf(p -> p.getId().equals(id));
        return removed ? "Producto eliminado con éxito." : "Producto no encontrado.";
    }
}
