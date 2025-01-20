package com.example.Ecommerce.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserAlreadyExists;
import com.example.Ecommerce.user.exceptions.UserNotFound;


@ControllerAdvice(basePackages = "com.example.Ecommerce.user.controller.UserControllers")
public class UserExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    private ResponseEntity<String> userAlreadyExists(UserAlreadyExists e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
    }
    
    @ExceptionHandler
    private ResponseEntity<String> internalError(RuntimeException e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + "erro interno, estamos trabalhando para resolver");
    }
    
}
