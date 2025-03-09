package com.example.Ecommerce.saque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.saque.service.SaqueServices;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/saque")
public class SaqueControllers {

    private final SaqueServices saqueServices;

    public SaqueControllers(SaqueServices saqueServices) {
        this.saqueServices = saqueServices;
    }

    @PostMapping("/sacar/{id}")
    public ResponseEntity<String> realizarSaque(@PathVariable String id) throws StripeException {

        saqueServices.sacar(id);

        return ResponseEntity.ok().body("Saque Realizado com sucesso!");
   
    }

    @GetMapping("/saques")
    public ResponseEntity<List<Saque>> verSaques() {

        List<Saque> saques = saqueServices.getSaques();

        return ResponseEntity.ok().body(saques);

    }
    
}
