package com.example.Ecommerce.favorito.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.service.FavoritoServices;

@RestController
@RequestMapping("/favorito")
public class FavoritoControllers {

    private final FavoritoServices favoritoServices;

    public FavoritoControllers(FavoritoServices favoritoServices) {
        this.favoritoServices = favoritoServices;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> adicionar(@PathVariable String id) {

        try {
            favoritoServices.addFavorito(id);

            return ResponseEntity.ok().body("produto adicionado aos favoritos com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

    @GetMapping("/meus-favoritos")
    public ResponseEntity<List<Favorito>> viewFavoritos() {

        List<Favorito> favoritos = favoritoServices.getFavoritos();

        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavorito(@PathVariable String id) {

        try {
            favoritoServices.deleteFavorito(id);

            return ResponseEntity.ok().body("Produto deletado de favoritos com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }
    
}
