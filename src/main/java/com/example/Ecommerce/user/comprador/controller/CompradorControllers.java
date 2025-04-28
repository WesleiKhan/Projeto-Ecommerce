package com.example.Ecommerce.user.comprador.controller;

import com.example.Ecommerce.user.comprador.service.CompradorEntryEditDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.user.comprador.service.CompradorEntryDTO;
import com.example.Ecommerce.user.comprador.service.CompradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comprador")
public class CompradorControllers {

    private final CompradorService compradorService;

    public CompradorControllers(CompradorService compradorService) {
        this.compradorService = compradorService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody CompradorEntryDTO data) {
 
        compradorService.createComprador(data);

        return ResponseEntity.ok().body("Usuario cadstrado agora esta apto a realizar compras");
  
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody CompradorEntryEditDTO data) {

        compradorService.updateComprador(data);

        return ResponseEntity.ok().body("Cadastro comprador atualizado com " +
                "sucesso.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComprador() {

        compradorService.deleteComprador();

        return ResponseEntity.ok().body("cadastro de comprador excluido com " +
                "sucesso!");
    }
    
}
