package com.technova.shopverse.service;

import com.technova.shopverse.model.Product;
import com.technova.shopverse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public List<Product> getAllProductsService() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByIdService(Long id) {
        return productRepository.findById(id);
    }


    public Product createProductService(Product product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        return productRepository.save(product);
    }

    public Product updateProductService(Long id, Product updated) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }
        if (updated.getName() == null || updated.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (updated.getPrice() == null || updated.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        Product product = optionalProduct.get();
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        return productRepository.save(product);
    }

    public void deleteProductService(Long id) {
        productRepository.deleteById(id);
    }
}
