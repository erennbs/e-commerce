package com.ecommerce.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex){
        Map<String, String> errors = new HashMap<>();
        log.warn("Product not found: {}", ex.getMessage());
        errors.put("message", "Product not found!");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<Map<String, String>> handleProductPurchaseException(ProductPurchaseException ex){
        Map<String, String> errors = new HashMap<>();
        log.warn("Product purchase exception: {}", ex.getMessage());
        errors.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(errors);
    }
}
