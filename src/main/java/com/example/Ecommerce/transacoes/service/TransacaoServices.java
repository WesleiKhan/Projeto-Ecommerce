package com.example.Ecommerce.transacoes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.transacoes.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.service.stripe.StripePaymentServices;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class TransacaoServices {

    private final TransacaoRepository transacaoRepository;

    private final AnuncioRepository anuncioRepository;

    private final UserServices userServices;

    private final CompradorRepository compradorRepository;

    private final VendedorRepository vendedorRepository;

    private final StripePaymentServices stripePaymentServices;

    public TransacaoServices(TransacaoRepository transacaoRepository,
                             AnuncioRepository anuncioRepository,
                             UserServices userServices,
                             CompradorRepository compradorRepository,
                             VendedorRepository vendedorRepository,
                             StripePaymentServices stripePaymentServices) {

        this.transacaoRepository = transacaoRepository;
        this.anuncioRepository =anuncioRepository;
        this.userServices = userServices;
        this.compradorRepository = compradorRepository;
        this.vendedorRepository = vendedorRepository;
        this.stripePaymentServices = stripePaymentServices;

    }


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

            throw new UserNotFound("Usuario não foi encontrado no Cadatro de" +
                    " Compradores.");
        }
  
    }

    public List<Transacao> getTransacao() {

        User user = userServices.getLoggedInUser();

        Optional<Vendedor> veOptional = vendedorRepository.findByNome(user);

        if (veOptional.isPresent()) {

            Vendedor vendedor = veOptional.get();

            return transacaoRepository.findByProdutoVendedor(vendedor);

        } else {
            throw new UserNotFound();
        }
    }
}
