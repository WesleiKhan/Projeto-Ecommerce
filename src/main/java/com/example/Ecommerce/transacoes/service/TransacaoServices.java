package com.example.Ecommerce.transacoes.service;

import java.math.BigDecimal;
import java.util.List;
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
import com.example.Ecommerce.utils.service.StripePaymentServices;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.example.Ecommerce.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

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

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private StripePaymentServices stripePaymentServices;


    public Transacao createTrasacao(String id, TransacaoEntryDTO data) throws StripeException {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Comprador> comprador = compradorRepository.findByNome(user);

        if (comprador.isPresent()) {

            Comprador infoComprador = comprador.get();

            Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

            BigDecimal valor = anuncio.getValor();

            BigDecimal valor_total = valor.multiply(BigDecimal.valueOf(data.getQuantidade())) ;

            long valor_total_centavos = valor_total.multiply(new BigDecimal("100")).longValueExact();

            String id_charge = stripePaymentServices.createPaymentIntent(data.getToken(), valor_total_centavos);

            Transacao newTransacao = new Transacao(data.getQuantidade());

            newTransacao.setProduto(anuncio);

            newTransacao.setComprador(infoComprador);

            newTransacao.setValor_total(valor_total);

            newTransacao.setId_charge_stripe(id_charge);

            return transacaoRepository.save(newTransacao);

        } else {

            throw new UserNotFound("Comprador não foi encontrado!");
        }
  
    }

    public List<Transacao> getTransacao() {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId = userDetails.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());

        Optional<Vendedor> veOptional = vendedorRepository.findByNome(user);

        if (veOptional.isPresent()) {

            Vendedor vendedor = veOptional.get();

            return transacaoRepository.findByProdutoVendedor(vendedor);

        } else {
            throw new UserNotFound("vendedor nao foi enconterado !");
        }
    }
}
