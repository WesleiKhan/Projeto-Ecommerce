package com.example.Ecommerce.saque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.user.exceptions.UserNotFound;

@ControllerAdvice(basePackages = "com.example.Ecommerce.saque.controller")
public class SaqueExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e);
    }

    @ExceptionHandler(SaqueInvalidoException.class)
    private ResponseEntity<String> saqueInvalidoException(SaqueInvalidoException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
    }
    
}
