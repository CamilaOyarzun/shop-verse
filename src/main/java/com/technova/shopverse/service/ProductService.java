package com.technova.shopverse.service;

import com.technova.shopverse.dto.CreateProductDTO;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProductsService();
    Optional<ProductDTO> getProductByIdService(Long id);
    ProductDTO createProductService(CreateProductDTO product);
    ProductDTO updateProductService(Long id, CreateProductDTO product);
    void deleteProductService(Long id);
    List<ProductDTO> getByCategoryId(Long categoryId);
}
