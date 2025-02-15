package com.example.Ecommerce.comprador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        try {
            compradorServices.createComprador(data);

            return ResponseEntity.ok().body("Usuario cadstrado agora esta apto a realizar compras");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro ao realizar cadastro de comprador " + e);
        }
    }
    
}
