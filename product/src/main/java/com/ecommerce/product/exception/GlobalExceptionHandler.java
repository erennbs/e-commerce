package com.ecommerce.product.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        log.warn("Validation failure on request: {}", ex.getMessage());

        List<String> messages = new ArrayList<>();
        for (ParameterValidationResult result : ex.getParameterValidationResults()) {
            Integer index = result.getContainerIndex();
            String prefix = index != null ? "Item[" + index + "]: " : "";
            for (MessageSourceResolvable error : result.getResolvableErrors()) {
                String msg = error.getDefaultMessage();
                if (msg != null) {
                    messages.add(prefix + msg);
                }
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Validation failure");
        body.put("errors", messages);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAnyException(Exception ex) {
        log.error("Unhandled exception in product service", ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage() != null ? ex.getMessage() : "Internal server error");
        return ResponseEntity.internalServerError().body(errors);
    }
}
