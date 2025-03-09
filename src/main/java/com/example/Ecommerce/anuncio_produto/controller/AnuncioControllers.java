package com.example.Ecommerce.anuncio_produto.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.service.AnuncioEntryDTO;
import com.example.Ecommerce.anuncio_produto.service.AnuncioServices;
import com.example.Ecommerce.utils.service.DTOs.CepEntryDTO;

@RestController
@RequestMapping("/anuncio")
public class AnuncioControllers {

    @Autowired
    private AnuncioServices anuncioServices;

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute AnuncioEntryDTO data)
            throws IOException {

        anuncioServices.createAnuncio(data);

        return ResponseEntity.ok().body("Anuncio de venda criado com sucesso!");
        
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edit(@ModelAttribute AnuncioEntryDTO data,
                                       @PathVariable String id)
            throws IOException {

        anuncioServices.updateAnuncio(id, data);

        return ResponseEntity.ok().body("O Anuncio do Produto Foi Editado " +
                "Com Sucesso.");

    }

    @PostMapping("/calcular-Frete/{id}")
    public ResponseEntity<String> getFrete(@PathVariable String id, @RequestBody CepEntryDTO cep) {

        String result = anuncioServices.verFrete(id, cep);

        return ResponseEntity.ok(result);
        
    }
    
    @GetMapping("/anuncios")
    public ResponseEntity<Page<Anuncio>> viewAnuncios(@RequestParam(defaultValue = "0") int page) {

        Page<Anuncio> anuncios = anuncioServices.getAnuncios(page);

        return ResponseEntity.ok(anuncios);
    }

    
}
