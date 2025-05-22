package com.technova.shopverse.dto;

import java.util.List;

public class CategoryDetailDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> productNames;

    public CategoryDetailDTO() {
    }

    public CategoryDetailDTO(Long id, String name, String description, List<String> productNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productNames = productNames;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProducts() {
        return productNames;
    }

    public void setProducts(List<String> productNames) {
        this.productNames = productNames;
    }
}
