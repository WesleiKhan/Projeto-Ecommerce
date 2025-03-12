package com.example.Ecommerce.vendedor.controller;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.example.Ecommerce.vendedor.controller")
public class VendedorExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    private ResponseEntity<String> userAlreadyExists(UserAlreadyExists e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response.get("Erro"));
    }
    
    @ExceptionHandler
    private ResponseEntity<String> internalError(RuntimeException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", "erro interno estamos tentando resolver!");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(StripeException.class)
    private ResponseEntity<String> stripeException(StripeException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<String> iOException(IOException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response.get(
                "Erro"));
    }
}
