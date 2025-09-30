package com.example.mercadolibreapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.mercadolibreapi.dto.ProductResponseDto;
import com.example.mercadolibreapi.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService implements IProductService {
    private final ObjectMapper objectMapper;
    private final File dataFile;
    private final boolean persistToFile;
    private List<ProductResponseDto> products;

    /**
     * Constructor por defecto (uso en producción):
     * carga datos desde classpath (products.json) y habilita persistencia en archivo.
     */
    public ProductService() {
        this(true, new File("src/main/resources/products.json"), new ObjectMapper());
    }

    // Constructor para tests o configuraciones personalizadas
    /**
     * Constructor alternativo que permite deshabilitar la escritura en disco.
     *
     * @param persistToFile si es false, no se persiste en el archivo JSON
     */
    public ProductService(boolean persistToFile) {
        this(persistToFile, new File("src/main/resources/products.json"), new ObjectMapper());
    }

    /**
     * Constructor completo para configurar persistencia y archivo de datos.
     *
     * @param persistToFile habilita/deshabilita la persistencia en archivo
     * @param dataFile archivo de datos donde se escribe la lista de productos
     * @param objectMapper instancia de Jackson para serializar/deserializar
     */
    public ProductService(boolean persistToFile, File dataFile, ObjectMapper objectMapper) {
        this.persistToFile = persistToFile;
        this.dataFile = dataFile;
        this.objectMapper = objectMapper;
        try {
            var inputStream = getClass().getClassLoader().getResourceAsStream("products.json");
            if (inputStream != null) {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(inputStream, ProductResponseDto[].class)));
            } else {
                products = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de productos", e);
        }
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return products;
    }

    @Override
    public ProductResponseDto getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto createProduct(ProductResponseDto product) {
        int nextId = products.stream()
            .mapToInt(ProductResponseDto::getId)
            .max()
            .orElse(0) + 1;
        product.setId(nextId);
        products.add(product);
    saveProducts();
    return product;
    }

    @Override
    public ProductResponseDto updateProduct(int id, ProductResponseDto product) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException("Producto no encontrado");
        }
        product.setId(id);
        products.set(index, product);
    saveProducts();
    return product;
    }

    @Override
    public void deleteProduct(int id) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException("Producto no encontrado");
        }
        products.remove(index);
        saveProducts();
    }

    /**
     * Persiste la lista en el archivo configurado si la opción está habilitada.
     * En tests suele estar deshabilitada para evitar efectos colaterales.
     *
     * @throws RuntimeException si ocurre un error de I/O al escribir el archivo
     */
    private void saveProducts() {
        if (!persistToFile) return;
        try {
            objectMapper.writeValue(dataFile, products);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en products.json", e);
        }
    }

}
