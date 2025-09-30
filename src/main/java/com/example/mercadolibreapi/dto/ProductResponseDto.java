
package com.example.mercadolibreapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {

    private int id;
    @NotBlank(message = "No debe estar vacío o null")
    private String name;
    @NotBlank(message = "No debe estar vacío o null")
    private String category;
    @Min(100)
    @Positive(message = "El precio debe ser un valor positivo")
    private int price;
    @Min(1)
    @Positive(message = "El stock debe ser un valor positivo")
    private int stock;
    private String description;

}
