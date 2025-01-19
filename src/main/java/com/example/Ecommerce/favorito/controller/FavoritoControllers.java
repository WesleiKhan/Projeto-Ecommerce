package com.example.Ecommerce.favorito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.favorito.service.FavoritoServices;

@RestController
@RequestMapping("/favorito")
public class FavoritoControllers {

    @Autowired
    private FavoritoServices favoritoServices;

    @PostMapping("/add/{id}")
    public ResponseEntity<String> adicionar(@PathVariable String id) {

        try {
            favoritoServices.addFavorito(id);

            return ResponseEntity.ok().body("produto adicionado aos favoritos com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }
    
}
