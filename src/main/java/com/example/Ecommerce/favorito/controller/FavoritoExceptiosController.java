package com.example.Ecommerce.favorito.controller;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.example.Ecommerce.favorito.controller")
public class FavoritoExceptiosController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AnuncioNotFound.class)
    private ResponseEntity<String> anuncioNotFound(AnuncioNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
