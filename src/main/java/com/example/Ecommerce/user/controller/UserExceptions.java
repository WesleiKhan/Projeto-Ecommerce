package com.example.Ecommerce.user.controller;

import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice(basePackages = "com.example.Ecommerce.user.controller")
public class UserExceptions extends ResponseEntityExceptionHandler {

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

        response.put("Erro", "erro interno, estamos trabalhando para resolver");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(UserNotAutorization.class)
    private ResponseEntity<String> userNotAutorization(UserNotAutorization e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response.get("Erro"));
    }
    
}
