package com.technova.shopverse.service;

import com.technova.shopverse.model.Product;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<Product> getAllProductsService();
    Optional<Product> getProductByIdService(Long id);
    Product createProductService(Product product);
    Product updateProductService(Long id, Product product);
    void deleteProductService(Long id);
}
