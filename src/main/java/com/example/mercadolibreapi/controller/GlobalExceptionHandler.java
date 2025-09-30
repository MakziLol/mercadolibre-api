package com.example.mercadolibreapi.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "codigo", 404,
                        "mensaje", ex.getMessage()
                ));
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
                var errores = ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> Map.of(
                                "campo", error.getField(),
                                "mensaje", error.getDefaultMessage()
                        ))
                        .toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "codigo", 400,
                                                "mensaje", "Datos inválidos",
                                                "errores", errores
                                ));
        }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "codigo", 500,
                        "mensaje", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "codigo", 500,
                        "mensaje", "Error inesperado",
                        "detalles", ex.getMessage()
                ));
    }
}
