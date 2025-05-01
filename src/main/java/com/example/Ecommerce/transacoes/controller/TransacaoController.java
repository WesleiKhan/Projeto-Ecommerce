package com.example.Ecommerce.transacoes.controller;

import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.transacoes.pagamento.service.PagamentoEntryDTO;
import com.example.Ecommerce.transacoes.pagamento.service.PagamentoService;
import com.example.Ecommerce.transacoes.saque.entity.Saque;
import com.example.Ecommerce.transacoes.saque.service.SaqueService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final PagamentoService pagamentoService;

    private final SaqueService saqueService;

    public TransacaoController( PagamentoService pagamentoService,
                                SaqueService saqueService) {

        this.pagamentoService = pagamentoService;
        this.saqueService = saqueService;
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

    @PostMapping("/sacar/{id}")
    public ResponseEntity<String> realizarSaque(@PathVariable String id) throws StripeException {

        saqueService.sacar(id);

        return ResponseEntity.ok().body("Saque Realizado com sucesso!");

    }

    @GetMapping("/saques")
    public ResponseEntity<List<Saque>> verSaques() {

        List<Saque> saques = saqueService.seeSaques();

        return ResponseEntity.ok().body(saques);

    }
}
