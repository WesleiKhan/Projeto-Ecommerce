package com.example.Ecommerce.user.vendedor.controller;

import com.example.Ecommerce.user.vendedor.service.VendedorEntryEditDTO;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.user.vendedor.service.VendedorEntryDTO;
import com.example.Ecommerce.user.vendedor.service.VendedorService;

import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping("/vendedor")
public class VendedorControllers {

    private final VendedorService vendedorService;

    public VendedorControllers(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody VendedorEntryDTO data)
    throws IOException, StripeException {

        vendedorService.createVendedor(data);

        return ResponseEntity.ok().body("Usuario Cadastrado Como " +
                "Vendedor Com Sucesso!");

    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody VendedorEntryEditDTO data) {

        vendedorService.updateVendedor(data);

        return ResponseEntity.ok().body("Cadastro De Vendedor Atualizado Com" +
                " Sucesso.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVendedor() throws Exception {

        vendedorService.deleteVendedor();

        return ResponseEntity.ok().body("Conta Foi Excluida Com Sucesso.");
    }
    
}
