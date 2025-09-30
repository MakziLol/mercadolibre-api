package com.example.mercadolibreapi.service;

import java.util.List;

import com.example.mercadolibreapi.dto.ProductResponseDto;

public interface IProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(int id);
    ProductResponseDto createProduct(ProductResponseDto product);
    ProductResponseDto updateProduct(int id, ProductResponseDto product);
    void deleteProduct(int id);
}
