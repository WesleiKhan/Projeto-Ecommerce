package com.example.Ecommerce.anuncio_produto.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.service.AnuncioEntryDTO;
import com.example.Ecommerce.anuncio_produto.service.AnuncioServices;

@RestController
@RequestMapping("/anuncio")
public class AnuncioControllers {

    @Autowired
    private AnuncioServices anuncioServices;

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute AnuncioEntryDTO data) {

        try {
            anuncioServices.createAnuncio(data);

            return ResponseEntity.ok().body("Anuncio de venda criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Error: " + e);
        }
    }
    
    @GetMapping("/anuncios")
    public ResponseEntity<List<Anuncio>> viewAnuncios() {

        List<Anuncio> anuncios = anuncioServices.getAnuncios();

        return ResponseEntity.ok(anuncios);
    }
}
