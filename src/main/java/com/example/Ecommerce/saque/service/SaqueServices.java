package com.example.Ecommerce.saque.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.saque.repositorie.SaqueRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.service.stripe.StripeTransferServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class SaqueServices {

    private final SaqueRepository saqueRepository;

    private final UserServices userServices;

    private final VendedorRepository vendedorRepository;

    private final TransacaoRepository transacaoRepository;

    private final StripeTransferServices stripeTransferServices;

    public SaqueServices(SaqueRepository saqueRepository,
                         UserServices userServices,
                         VendedorRepository vendedorRepository,
                         TransacaoRepository transacaoRepository,
                         StripeTransferServices stripeTransferServices) {

        this.saqueRepository = saqueRepository;
        this.userServices = userServices;
        this.vendedorRepository = vendedorRepository;
        this.transacaoRepository = transacaoRepository;
        this.stripeTransferServices = stripeTransferServices;
    }


    public void sacar(String id) throws StripeException {

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> venOptional = vendedorRepository.findByNome(user);

        if (venOptional.isPresent()) {

            Transacao transacao = transacaoRepository.findById(id).orElseThrow();

            if (saqueRepository.findByTransacao(transacao).isPresent()) {

                throw new SaqueInvalidoException();
            }

            BigDecimal desconto = transacao.getValor_total()
                    .multiply(new BigDecimal("0.10"));

            BigDecimal valor = transacao.getValor_total().subtract(desconto)
                    .setScale(2, RoundingMode.HALF_UP);

            long valorEmCentavos = valor.multiply(new BigDecimal("100")).longValueExact();

            Vendedor vendedor = venOptional.get();

            stripeTransferServices.createTransfer(vendedor.getId_account_stripe(),
                    transacao.getId_charge_stripe(), valorEmCentavos);

            Saque newSaque = new Saque(valor);

            newSaque.setSacador(vendedor);

            newSaque.setTransacao(transacao);

            saqueRepository.save(newSaque);

        } else {
            throw new UserNotFound("Vendedor não foi encontrador.");
        }
    }

    public List<Saque> getSaques() {

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> venOptional = vendedorRepository.findByNome(user);

        if (venOptional.isPresent()) {

            Vendedor vendedor = venOptional.get();

            return vendedor.getSaques();

        } else {
            throw new UserNotFound("Vendedor não foi encontrado.");
        }
    }
    
}
