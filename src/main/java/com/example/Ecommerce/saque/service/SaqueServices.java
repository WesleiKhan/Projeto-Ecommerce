package com.example.Ecommerce.saque.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.saque.execeptions.SaqueInvalidoException;
import com.example.Ecommerce.saque.repositorie.SaqueRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;

@Service
public class SaqueServices {

    @Autowired
    private SaqueRepository saqueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;


    public Saque sacar(String id) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Vendedor> venOptional = vendedorRepository.findByNome(user);

        if (venOptional.isPresent()) {

            Transacao transacao = transacaoRepository.findById(id).orElseThrow();

            if (saqueRepository.findByTransacao(transacao).isPresent()) {

                throw new SaqueInvalidoException();
            }

            BigDecimal desconto = transacao.getValor_total().multiply(new BigDecimal("0.10"));

            BigDecimal valor = transacao.getValor_total().subtract(desconto);

            //long valorEmCentavos = valor.multiply(new BigDecimal("100")).longValueExact();

            Vendedor vendedor = venOptional.get();

            Saque newSaque = new Saque(valor);

            newSaque.setSacador(vendedor);

            newSaque.setTransacao(transacao);

            return saqueRepository.save(newSaque);

        } else {
            throw new UserNotFound("vendedor não foi encontrador !");
        }
    }

    public List<Saque> getSaques() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Vendedor> venOptional = vendedorRepository.findByNome(user);

        if (venOptional.isPresent()) {

            Vendedor vendedor = venOptional.get();

            return vendedor.getSaques();

        } else {
            throw new UserNotFound("Vendedor não foi encontrado!");
        }
    }
    
}
