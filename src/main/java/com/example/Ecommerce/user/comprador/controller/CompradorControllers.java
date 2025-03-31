package com.example.Ecommerce.user.comprador.controller;

import com.example.Ecommerce.user.comprador.service.CompradorEntryEditDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.user.comprador.service.CompradorEntryDTO;
import com.example.Ecommerce.user.comprador.service.CompradorServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comprador")
public class CompradorControllers {

    private final CompradorServices compradorServices;

    public CompradorControllers(CompradorServices compradorServices) {
        this.compradorServices = compradorServices;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody CompradorEntryDTO data) {
 
        compradorServices.createComprador(data);

        return ResponseEntity.ok().body("Usuario cadstrado agora esta apto a realizar compras");
  
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody CompradorEntryEditDTO data) {

        compradorServices.updateComprador(data);

        return ResponseEntity.ok().body("Cadastro comprador atualizado com " +
                "sucesso.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComprador() {

        compradorServices.deleteComprador();

        return ResponseEntity.ok().body("cadastro de comprador excluido com " +
                "sucesso!");
    }
    
}
