package com.technova.shopverse.service.impl;

import com.technova.shopverse.dto.CreateProductDTO;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.repository.CategoryRepository;
import com.technova.shopverse.repository.ProductRepository;
import com.technova.shopverse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public List<ProductDTO> getAllProductsService() {
        return productRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ProductDTO> getProductByIdService(Long id) {
        return productRepository.findById(id).map(this::toDTO);
    }


    public ProductDTO createProductService(CreateProductDTO product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
        System.out.println(category.toString());
        Product newProduct = productRepository.save(new Product(product.getName(), product.getDescription(), product.getPrice(), category));
        return toDTO(newProduct);
    }

    public ProductDTO updateProductService(Long id, CreateProductDTO updated) {

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
        Category category = categoryRepository.findById(updated.getCategoryId()).orElse(null);
        Product product = optionalProduct.get();
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setCategory(category);
        return toDTO(productRepository.save(product));

    }

    public void deleteProductService(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName() : null);
    }

    public List<ProductDTO> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .toList();

    }
}