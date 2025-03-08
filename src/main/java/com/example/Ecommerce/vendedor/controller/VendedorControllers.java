package com.example.Ecommerce.vendedor.controller;

import com.example.Ecommerce.vendedor.service.VendedorEntryEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.vendedor.service.VendedorEntryDTO;
import com.example.Ecommerce.vendedor.service.VendedorServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendedor")
public class VendedorControllers {

    @Autowired
    private VendedorServices vendedorServices;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody VendedorEntryDTO data) {

        try {
            vendedorServices.createVendedor(data);

            return ResponseEntity.ok().body("Usuario conseguir se cadastra como vendedor com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody VendedorEntryEditDTO data) {

        vendedorServices.updateVendedor(data);

        return ResponseEntity.ok().body("Cadastro De Vendedor Atualizado com" +
                " sucesso.");
    }
    
}
