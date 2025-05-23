package com.example.Ecommerce.anuncio_produto.controller;

import java.io.IOException;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoEntryDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoResponseDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.service.AvaliacaoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.service.AnuncioEntryDTO;
import com.example.Ecommerce.anuncio_produto.service.AnuncioService;
import com.example.Ecommerce.client.service.DTOs.CepEntryDTO;

@RestController
@RequestMapping("/anuncio")
public class AnuncioControllers {

    private final AnuncioService anuncioService;

    private final AvaliacaoService avaliacaoService;

    public AnuncioControllers(AnuncioService anuncioService,
                              AvaliacaoService avaliacaoService) {

        this.anuncioService = anuncioService;
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute AnuncioEntryDTO data)
            throws IOException {

        anuncioService.createAnuncio(data);

        return ResponseEntity.ok().body("Anuncio de venda criado com sucesso!");
        
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> edit(@ModelAttribute AnuncioEntryDTO data,
                                       @PathVariable String id)
            throws IOException {

        anuncioService.updateAnuncio(id, data);

        return ResponseEntity.ok().body("O Anuncio do Produto Foi Editado " +
                "Com Sucesso.");

    }

    @PostMapping("/calcular-Frete/{id}")
    public ResponseEntity<String> getFrete(@PathVariable String id, @RequestBody CepEntryDTO cep) {

        String result = anuncioService.verFrete(id, cep);

        return ResponseEntity.ok(result);
        
    }
    
    @GetMapping("/anuncios")
    public ResponseEntity<Page<Anuncio>> viewAnuncios(@RequestParam(defaultValue = "0") int page) {

        Page<Anuncio> anuncios = anuncioService.getAnuncios(page);

        return ResponseEntity.ok(anuncios);
    }

    @PostMapping("/avaliar/{id}")
    public ResponseEntity<String> registerAvaliacao(@PathVariable String id,
                                                    @RequestBody AvaliacaoEntryDTO data) {
        avaliacaoService.register(id, data);

        return ResponseEntity.ok().body("Avaliação Realizada com sucesso.");
    }

    @GetMapping("/avaliacoes/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacoes(@PathVariable String id) {

        AvaliacaoResponseDTO avaliacoes = avaliacaoService.seeAvaliacoes(id);

        return ResponseEntity.ok().body(avaliacoes);
    }
    
}
