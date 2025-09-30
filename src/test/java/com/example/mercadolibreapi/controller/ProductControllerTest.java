package com.example.mercadolibreapi.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mercadolibreapi.dto.ProductResponseDto;
import com.example.mercadolibreapi.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProductService productService;

    @Test
    void getProductById_ReturnsProduct() throws Exception {
        ProductResponseDto product = ProductResponseDto.builder().id(1).name("Test").category("Cat").price(100).stock(10).build();
        Mockito.when(productService.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/api/products/get/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void getAllProducts_ReturnsList() throws Exception {
        List<ProductResponseDto> products = Arrays.asList(ProductResponseDto.builder().id(1).name("A").category("B").price(100).stock(10).build());
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/get"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }

    @Test
    void createProduct_ReturnsCreated() throws Exception {
        ProductResponseDto product = ProductResponseDto.builder().id(1).name("A").category("B").price(100).stock(10).build();
        Mockito.when(productService.createProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void updateProduct_ReturnsUpdated() throws Exception {
        ProductResponseDto product = ProductResponseDto.builder().id(1).name("A").category("B").price(100).stock(10).build();
        Mockito.when(productService.updateProduct(eq(1), any())).thenReturn(product);

        mockMvc.perform(put("/api/products/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void deleteProduct_ReturnsNoContent() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/delete/{id}", 1))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}


