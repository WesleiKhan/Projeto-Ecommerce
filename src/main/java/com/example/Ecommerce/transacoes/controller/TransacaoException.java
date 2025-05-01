package com.example.Ecommerce.transacoes.controller;

import com.example.Ecommerce.transacoes.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.example.Ecommerce.transacoes.controller" )
public class TransacaoException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(SaqueInvalidoException.class)
    private ResponseEntity<String> saqueInvalidoException(SaqueInvalidoException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response.get("Erro"));
    }
}
