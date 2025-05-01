package com.example.Ecommerce.transacoes.pagamento.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecommerce.transacoes.pagamento.entity.Transacao;
import com.example.Ecommerce.transacoes.pagamento.service.TransacaoEntryDTO;
import com.example.Ecommerce.transacoes.pagamento.service.TransacaoServices;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/transacoes")
public class TransacaoControllers {

    private final TransacaoServices transacaoServices;

    public TransacaoControllers(TransacaoServices transacaoServices) {
        this.transacaoServices = transacaoServices;
    }

    @PostMapping("/comprar/{id}")
    public ResponseEntity<String> comprar(@PathVariable String id, @RequestBody TransacaoEntryDTO data) throws StripeException {

        transacaoServices.createTrasacao(id, data);

        return ResponseEntity.ok().body("Compra realizada com sucesso !");
       
    }

    @GetMapping("/ver-transacoes")
    public ResponseEntity<List<Transacao>> verTransacoes() {

        List<Transacao> transacoes = transacaoServices.getTransacao();

        return ResponseEntity.ok().body(transacoes);
    
    }
    
}
