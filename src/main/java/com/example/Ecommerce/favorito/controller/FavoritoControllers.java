package com.example.Ecommerce.favorito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.favorito.service.FavoritoService;

@RestController
@RequestMapping("/favorito")
public class FavoritoControllers {

    private final FavoritoService favoritoService;

    public FavoritoControllers(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> adicionar(@PathVariable String id) {

        favoritoService.addItem(id);

        return ResponseEntity.ok().body("produto adicionado aos favoritos com sucesso!");
    }

    @GetMapping("/meus-favoritos")
    public ResponseEntity<List<Favorito>> viewFavoritos() {

        List<Favorito> favoritos = favoritoService.getFavoritos();

        return ResponseEntity.ok(favoritos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavorito(@PathVariable String id) {

        favoritoService.deleteItem(id);

        return ResponseEntity.ok().body("Produto deletado de favoritos com sucesso!");

    }
    
}
