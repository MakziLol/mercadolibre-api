package com.example.mercadolibreapi.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mercadolibreapi.dto.ProductResponseDto;

class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
    productService = new ProductService(false);
    }

    @Test
    void getAllProducts_ReturnsList() {
        List<ProductResponseDto> products = productService.getAllProducts();
        assertNotNull(products);
    }

    @Test
    void createProduct_AddsProduct() {
        ProductResponseDto product = ProductResponseDto.builder().name("Nuevo").category("Cat").price(150).stock(5).build();
        ProductResponseDto created = productService.createProduct(product);
        assertNotNull(created);
        assertTrue(created.getId() > 0);
        assertEquals("Nuevo", created.getName());
    }

    @Test
    void getProductById_ReturnsProduct() {
        ProductResponseDto product = ProductResponseDto.builder().name("Test").category("Cat").price(200).stock(10).build();
        ProductResponseDto created = productService.createProduct(product);
        ProductResponseDto found = productService.getProductById(created.getId());
        assertEquals(created.getId(), found.getId());
    }

    @Test
    void updateProduct_UpdatesProduct() {
        ProductResponseDto product = ProductResponseDto.builder().name("Test").category("Cat").price(200).stock(10).build();
        ProductResponseDto created = productService.createProduct(product);
        created.setName("Actualizado");
        ProductResponseDto updated = productService.updateProduct(created.getId(), created);
        assertEquals("Actualizado", updated.getName());
    }

    @Test
    void deleteProduct_RemovesProduct() {
        ProductResponseDto product = ProductResponseDto.builder().name("Test").category("Cat").price(200).stock(10).build();
        ProductResponseDto created = productService.createProduct(product);
    assertDoesNotThrow(() -> productService.deleteProduct(created.getId()));
    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> productService.getProductById(created.getId()));
    assertNotNull(thrown);
    }
}
