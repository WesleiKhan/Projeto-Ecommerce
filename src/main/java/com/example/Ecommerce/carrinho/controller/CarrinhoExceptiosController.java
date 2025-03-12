package com.example.Ecommerce.carrinho.controller;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.example.Ecommerce.carrinho.controller")
public class CarrinhoExceptiosController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AnuncioNotFound.class)
    private ResponseEntity<String> anuncioNotFound(AnuncioNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro");
    }

    @ExceptionHandler(UserNotAutorization.class)
    private ResponseEntity<String> userNotAutorization(UserNotAutorization e) {

        Map<String, String> response = new HashMap<>();

        response.put("Message", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response.get("Message"));
    }
}
