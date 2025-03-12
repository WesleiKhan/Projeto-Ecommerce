package com.example.Ecommerce.vendedor.controller;

import com.example.Ecommerce.vendedor.service.VendedorEntryEditDTO;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Ecommerce.vendedor.service.VendedorEntryDTO;
import com.example.Ecommerce.vendedor.service.VendedorServices;

import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping("/vendedor")
public class VendedorControllers {

    private final VendedorServices vendedorServices;

    public VendedorControllers(VendedorServices vendedorServices) {
        this.vendedorServices = vendedorServices;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody VendedorEntryDTO data)
    throws IOException, StripeException {

        vendedorServices.createVendedor(data);

        return ResponseEntity.ok().body("Usuario Cadastrado Como " +
                "Vendedor Com Sucesso!");

    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody VendedorEntryEditDTO data) {

        vendedorServices.updateVendedor(data);

        return ResponseEntity.ok().body("Cadastro De Vendedor Atualizado Com" +
                " Sucesso.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVendedor() throws Exception {

        vendedorServices.deleteVendedor();

        return ResponseEntity.ok().body("Conta Foi Excluida Com Sucesso.");
    }
    
}
