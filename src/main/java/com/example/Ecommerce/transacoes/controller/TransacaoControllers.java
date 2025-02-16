package com.example.Ecommerce.transacoes.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.transacoes.service.TransacaoEntryDTO;
import com.example.Ecommerce.transacoes.service.TransacaoServices;

@RestController
@RequestMapping("/transacoes")
public class TransacaoControllers {

    @Autowired
    private TransacaoServices transacaoServices;

    @PostMapping("/comprar/{id}")
    public ResponseEntity<String> comprar(@PathVariable String id, @RequestBody TransacaoEntryDTO data) {

        try {
            transacaoServices.createTrasacao(id, data);

            return ResponseEntity.ok().body("Compra realizada com sucesso !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Error ao realizar compra " + e);
        }
    }
    
}
