package com.example.mercadolibreapi.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNoSuchElementException_ReturnsNotFound() {
        NoSuchElementException ex = new NoSuchElementException("No encontrado");
        ResponseEntity<?> response = handler.handleNoSuchElementException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Map<?,?> body = (Map<?,?>) response.getBody();
    assertNotNull(body);
    assertEquals(404, body.get("codigo"));
    assertEquals("No encontrado", body.get("mensaje"));
    }

    @Test
    void handleRuntimeException_ReturnsInternalServerError() {
        RuntimeException ex = new RuntimeException("Error interno");
        ResponseEntity<?> response = handler.handleRuntimeException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    Map<?,?> body = (Map<?,?>) response.getBody();
    assertNotNull(body);
    assertEquals(500, body.get("codigo"));
    assertEquals("Error interno", body.get("mensaje"));
    }

    @Test
    void handleGeneralException_ReturnsInternalServerError() {
        Exception ex = new Exception("Detalles del error");
        ResponseEntity<?> response = handler.handleGeneralException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    Map<?,?> body = (Map<?,?>) response.getBody();
    assertNotNull(body);
    assertEquals(500, body.get("codigo"));
    assertEquals("Error inesperado", body.get("mensaje"));
    assertEquals("Detalles del error", body.get("detalles"));
    }
}
