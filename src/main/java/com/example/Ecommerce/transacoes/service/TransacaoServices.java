package com.example.Ecommerce.transacoes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.repositorie.UserRepository;
import com.example.Ecommerce.utils.service.stripe.StripePaymentServices;
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
    private UserServices userServices;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private StripePaymentServices stripePaymentServices;


    public void createTrasacao(String id, TransacaoEntryDTO data) throws StripeException {

        User user = userServices.getLoggedInUser();

        Optional<Comprador> comprador = compradorRepository.findByNome(user);

        if (comprador.isPresent()) {

            Comprador infoComprador = comprador.get();

            Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

            BigDecimal valor = anuncio.getValor();

            BigDecimal valor_total =
                    valor.multiply(BigDecimal.valueOf(data.getQuantidade()));

            long valor_total_centavos = valor_total.multiply(new BigDecimal(
                    "100")).longValueExact();

            String id_charge =
                    stripePaymentServices.createPaymentIntent(data.getToken(), valor_total_centavos);

            Transacao newTransacao = new Transacao(data.getQuantidade());

            newTransacao.setProduto(anuncio);

            newTransacao.setComprador(infoComprador);

            newTransacao.setValor_total(valor_total);

            newTransacao.setId_charge_stripe(id_charge);

            transacaoRepository.save(newTransacao);

        } else {

            throw new UserNotFound("Comprador não foi encontrado!");
        }
  
    }

    public List<Transacao> getTransacao() {

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> veOptional = vendedorRepository.findByNome(user);

        if (veOptional.isPresent()) {

            Vendedor vendedor = veOptional.get();

            return transacaoRepository.findByProdutoVendedor(vendedor);

        } else {
            throw new UserNotFound("vendedor nao foi enconterado !");
        }
    }
}
