package com.example.Ecommerce.saque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.saque.service.SaqueEntryDTO;
import com.example.Ecommerce.saque.service.SaqueServices;

@RestController
@RequestMapping("/saque")
public class SaqueControllers {

    @Autowired
    private SaqueServices saqueServices;

    @PostMapping("/sacar/{id}")
    public ResponseEntity<String> realizarSaque(@PathVariable String id, @RequestBody SaqueEntryDTO data) {

        try {
            saqueServices.sacar(id, data);

            return ResponseEntity.ok().body("Saque Realizado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e);
        }
    }

    @GetMapping("/saques")
    public ResponseEntity<List<Saque>> verSaques() {

        try {
            List<Saque> saques = saqueServices.getSaques();

            return ResponseEntity.ok().body(saques);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
}
