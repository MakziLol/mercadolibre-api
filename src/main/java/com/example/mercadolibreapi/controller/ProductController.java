package com.example.mercadolibreapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercadolibreapi.dto.ProductResponseDto;
import com.example.mercadolibreapi.service.IProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final IProductService productService;

    /**
     * Obtiene un producto por su identificador.
     *
     * Ruta: GET /api/products/get/{id}
     *
     * @param id identificador del producto
     * @return 200 OK con el {@link ProductResponseDto} en el cuerpo
     *         (404 Not Found si no existe; manejado por {@link GlobalExceptionHandler})
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
    ProductResponseDto product = productService.getProductById(id);
    return ResponseEntity.ok(product);
    }

    /**
     * Lista todos los productos disponibles.
     *
     * Ruta: GET /api/products/get
     *
     * @return lista de {@link ProductResponseDto}
     */
    @GetMapping("/get")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Crea un nuevo producto.
     *
     * Ruta: POST /api/products/create
     *
     * Reglas de validación aplicadas sobre {@link ProductResponseDto} (name, category, price, stock).
     *
     * @param product datos del producto a crear
     * @return 201 Created con el producto creado (incluye id asignado)
     *         o 400 Bad Request si falla la validación (manejado por {@link GlobalExceptionHandler})
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductResponseDto product) {
        ProductResponseDto created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un producto existente.
     *
     * Ruta: PUT /api/products/update/{id}
     *
     * @param id identificador del producto a actualizar
     * @param product datos a actualizar (se fuerza el id indicado)
     * @return 200 OK con el producto actualizado
     *         (404 Not Found si no existe; 400 si falla validación; ambos manejados por {@link GlobalExceptionHandler})
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductResponseDto product) {
        ProductResponseDto updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un producto por su identificador.
     *
     * Ruta: DELETE /api/products/delete/{id}
     *
     * @param id identificador del producto
     * @return 204 No Content si se eliminó correctamente
     *         (404 Not Found si no existe; manejado por {@link GlobalExceptionHandler})
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
    }

}
