package com.example.Ecommerce.transacoes.pagamento.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.Ecommerce.user.service.UserService;
import com.example.Ecommerce.client.service.stripe.contract.StripePayment;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.transacoes.pagamento.entity.Transacao;
import com.example.Ecommerce.transacoes.pagamento.repositorie.TransacaoRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.vendedor.repositorie.VendedorRepository;
import com.stripe.exception.StripeException;

@Service
public class TransacaoServices {

    private final TransacaoRepository transacaoRepository;

    private final AnuncioRepository anuncioRepository;

    private final UserService userService;

    private final CompradorRepository compradorRepository;

    private final VendedorRepository vendedorRepository;

    private final StripePayment stripePayment;

    public TransacaoServices(TransacaoRepository transacaoRepository,
                             AnuncioRepository anuncioRepository,
                             UserService userService,
                             CompradorRepository compradorRepository,
                             VendedorRepository vendedorRepository,
                             StripePayment stripePayment) {

        this.transacaoRepository = transacaoRepository;
        this.anuncioRepository =anuncioRepository;
        this.userService = userService;
        this.compradorRepository = compradorRepository;
        this.vendedorRepository = vendedorRepository;
        this.stripePayment = stripePayment;

    }


    public void createTrasacao(String id, TransacaoEntryDTO data) throws StripeException {

        User user = userService.getLoggedInUser();

        Comprador infoComprador = compradorRepository.findByNome(user)
                .orElseThrow(() -> new UserNotFound("Usuario não foi encontrado no Cadatro de" +
                        " Compradores."));


        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow();

        BigDecimal valor = anuncio.getValor();

        BigDecimal valor_total = valor.multiply(BigDecimal.valueOf(data.getQuantidade()));

        long valor_total_centavos = valor_total.multiply(new BigDecimal(
                    "100")).longValueExact();

        String id_charge = stripePayment.createPaymentIntent(data.getToken(), valor_total_centavos);

        Transacao newTransacao = new Transacao(data.getQuantidade());

        newTransacao.setProduto(anuncio);

        newTransacao.setComprador(infoComprador);

        newTransacao.setValor_total(valor_total);

        newTransacao.setId_charge_stripe(id_charge);

        transacaoRepository.save(newTransacao);

    }

    public List<Transacao> getTransacao() {

        User user = userService.getLoggedInUser();

        Vendedor vendedor = vendedorRepository.findByNome(user)
                .orElseThrow(UserNotFound::new);

        return transacaoRepository.findByProdutoVendedor(vendedor);

    }
}
