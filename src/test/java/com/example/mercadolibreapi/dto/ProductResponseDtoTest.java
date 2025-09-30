package com.example.mercadolibreapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ProductResponseDtoTest {
    @Test
    void builderAndGettersSetters() {
        ProductResponseDto dto = ProductResponseDto.builder()
                .id(1)
                .name("Test")
                .category("Cat")
                .price(200)
                .stock(5)
                .description("Desc")
                .build();
        assertEquals(1, dto.getId());
        assertEquals("Test", dto.getName());
        assertEquals("Cat", dto.getCategory());
        assertEquals(200, dto.getPrice());
        assertEquals(5, dto.getStock());
        assertEquals("Desc", dto.getDescription());
        dto.setName("Nuevo");
        assertEquals("Nuevo", dto.getName());
    }

    @Test
    void noArgsConstructor() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(2);
        dto.setName("A");
        dto.setCategory("B");
        dto.setPrice(300);
        dto.setStock(10);
        dto.setDescription("D");
        assertEquals(2, dto.getId());
        assertEquals("A", dto.getName());
        assertEquals("B", dto.getCategory());
        assertEquals(300, dto.getPrice());
        assertEquals(10, dto.getStock());
        assertEquals("D", dto.getDescription());
    }
}
