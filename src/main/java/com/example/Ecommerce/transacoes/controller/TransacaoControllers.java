package com.example.Ecommerce.transacoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.service.TransacaoEntryDTO;
import com.example.Ecommerce.transacoes.service.TransacaoServices;

@RestController
@RequestMapping("/transacoes")
public class TransacaoControllers {

    @Autowired
    private TransacaoServices transacaoServices;

    @PostMapping("/comprar/{id}")
    public ResponseEntity<String> comprar(@PathVariable String id, @RequestBody TransacaoEntryDTO data) {

        transacaoServices.createTrasacao(id, data);

        return ResponseEntity.ok().body("Compra realizada com sucesso !");
       
    }

    @GetMapping("/ver-transacoes")
    public ResponseEntity<List<Transacao>> verTransacoes() {

        List<Transacao> transacoes = transacaoServices.getTransacao();

        return ResponseEntity.ok().body(transacoes);
    
    }
    
}
