package com.example.Ecommerce.user.comprador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;

@ControllerAdvice(basePackages = "com.example.Ecommerce.comprador.controller")
public class CompradorExceptions extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExists.class)
    private ResponseEntity<String> userAlreadyExists(UserAlreadyExists e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
    }
}
