package com.example.Ecommerce.transacoes.saque.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.client.service.stripe.contract.StripeTransfer;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.transacoes.saque.entity.Saque;
import com.example.Ecommerce.transacoes.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.transacoes.saque.repositorie.SaqueRepository;
import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.transacoes.pagamento.repositorie.PagamentoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class SaqueService {

    private final SaqueRepository saqueRepository;

    private final UserService userService;

    private final VendedorRepository vendedorRepository;

    private final PagamentoRepository pagamentoRepository;

    private final StripeTransfer stripeTransfer;

    public SaqueService(SaqueRepository saqueRepository,
                        UserService userService,
                        VendedorRepository vendedorRepository,
                        PagamentoRepository pagamentoRepository,
                        StripeTransfer stripeTransfer) {

        this.saqueRepository = saqueRepository;
        this.userService = userService;
        this.vendedorRepository = vendedorRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.stripeTransfer = stripeTransfer;
    }


    public void sacar(String id) throws StripeException {

        User user = userService.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Vendedor não foi encontrador."));

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trasação não " +
                        "encontrada"));

        if (saqueRepository.findByTransacao(pagamento).isPresent()) {

            throw new SaqueInvalidoException();
        }

        BigDecimal desconto = pagamento.getValor_total().multiply(new BigDecimal("0.10"));

        BigDecimal valor = pagamento.getValor_total().subtract(desconto)
                    .setScale(2, RoundingMode.HALF_UP);

        long valorEmCentavos = valor.multiply(new BigDecimal("100")).longValueExact();

        stripeTransfer.createTransfer(vendedor.getId_account_stripe(),
                    pagamento.getId_charge_stripe(), valorEmCentavos);

        Saque newSaque = new Saque(valor);

        newSaque.setSacador(vendedor);

        newSaque.setTransacao(pagamento);

        saqueRepository.save(newSaque);

    }

    public List<Saque> seeSaques() {

        User user = userService.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(() ->  new UserNotFound("Vendedor não foi encontrado."));

        return vendedor.getSaques();

    }
    
}
