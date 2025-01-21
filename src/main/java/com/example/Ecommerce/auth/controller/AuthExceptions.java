package com.example.Ecommerce.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserNotFound;

@ControllerAdvice(basePackages = "com.example.Ecommerce.auth.controller.AuthControllers")
public class AuthExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFound(UserNotFound e) {
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message: " + e.getMessage());
    }
    
}
