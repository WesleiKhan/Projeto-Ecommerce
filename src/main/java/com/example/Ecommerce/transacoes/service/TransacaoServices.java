package com.example.Ecommerce.transacoes.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.auth.service.CustomUserDetails;
import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;

@Service
public class TransacaoServices {
    
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    public Transacao createTrasacao(String id, TransacaoEntryDTO data) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Comprador> comprador = compradorRepository.findByNome(user);

        if (comprador.isPresent()) {

            Comprador infoComprador = comprador.get();

            Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

            BigDecimal valor = anuncio.getValor();

            BigDecimal valor_total = valor.multiply(BigDecimal.valueOf(data.getQuantidade())) ;

            Transacao newTransacao = new Transacao(data.getQuantidade());

            newTransacao.setProduto(anuncio);

            newTransacao.setComprador(infoComprador);

            newTransacao.setValor_total(valor_total);

            return transacaoRepository.save(newTransacao);

        } else {

            throw new RuntimeException();
        }

        
    }
}
