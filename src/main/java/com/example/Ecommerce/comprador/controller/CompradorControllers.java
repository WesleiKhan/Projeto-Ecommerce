package com.example.Ecommerce.comprador.controller;

import com.example.Ecommerce.comprador.service.CompradorEntryEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.comprador.service.CompradorEntryDTO;
import com.example.Ecommerce.comprador.service.CompradorServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comprador")
public class CompradorControllers {

    @Autowired
    private CompradorServices compradorServices;

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
    
}
