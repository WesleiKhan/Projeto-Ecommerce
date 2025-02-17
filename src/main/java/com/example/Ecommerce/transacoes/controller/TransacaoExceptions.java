package com.example.Ecommerce.transacoes.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserNotFound;

@ControllerAdvice(basePackages = "com.example.Ecommerce.transacoes.controller.TransacaoControllers")
public class TransacaoExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Error: " + e);
    }
    
}
