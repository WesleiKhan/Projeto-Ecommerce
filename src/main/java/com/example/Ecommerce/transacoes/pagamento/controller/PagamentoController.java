package com.example.Ecommerce.transacoes.pagamento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.transacoes.pagamento.service.PagamentoEntryDTO;
import com.example.Ecommerce.transacoes.pagamento.service.PagamentoService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/transacoes")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/comprar/{id}")
    public ResponseEntity<String> comprar(@PathVariable String id, @RequestBody PagamentoEntryDTO data) throws StripeException {

        pagamentoService.makePayment(id, data);

        return ResponseEntity.ok().body("Compra realizada com sucesso !");
       
    }

    @GetMapping("/ver-transacoes")
    public ResponseEntity<List<Pagamento>> verTransacoes() {

        List<Pagamento> transacoes = pagamentoService.seeTransacao();

        return ResponseEntity.ok().body(transacoes);
    
    }
    
}
