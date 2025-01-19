package com.example.Ecommerce.carrinho.controller;

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

import com.example.Ecommerce.carrinho.entity.Carrinho;
import com.example.Ecommerce.carrinho.service.CarrinhoEntryDTO;
import com.example.Ecommerce.carrinho.service.CarrinhoServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoControllers {

    @Autowired
    private CarrinhoServices carrinhoServices;

    @PostMapping("/add/{id}")
    public ResponseEntity<String> adicionar(@PathVariable String id, @Valid @RequestBody CarrinhoEntryDTO data) {

        try {
            carrinhoServices.addCarrinho(id, data);

            return ResponseEntity.ok().body("Produto adicionado ao carrinho com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e);
        }
    }

    @GetMapping("/meu-carrinho")
    public ResponseEntity<List<Carrinho>> viewCarrinhos() {

        List<Carrinho> carrinhos = carrinhoServices.getCarrinhos();

        return ResponseEntity.ok(carrinhos);
    }
    
}
