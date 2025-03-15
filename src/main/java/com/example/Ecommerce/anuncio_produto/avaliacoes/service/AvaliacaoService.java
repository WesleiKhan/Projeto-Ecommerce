package com.example.Ecommerce.anuncio_produto.avaliacoes.service;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoEntryDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoResponseDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.avaliacoes.repositorie.AvaliacaoRepository;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.comprador.entity.Comprador;
import com.example.Ecommerce.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final UserServices userServices;

    private final CompradorRepository compradorRepository;

    private final AnuncioRepository anuncioRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            UserServices userServices,
                            CompradorRepository compradorRepository,
                            AnuncioRepository anuncioRepository) {

        this.avaliacaoRepository = avaliacaoRepository;
        this.userServices = userServices;
        this.compradorRepository = compradorRepository;
        this.anuncioRepository = anuncioRepository;
    }

    public void register(String id, AvaliacaoEntryDTO data) {

        User user = userServices.getLoggedInUser();

        Comprador comprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não " +
                                "foi encontrado"));

        List<Transacao> transacaos = comprador.getTransacoes();

        Anuncio anuncio = anuncioRepository.findById(id)
                        .orElseThrow(AnuncioNotFound::new);

        boolean transacaoEncontrada = transacaos.stream()
                .anyMatch(transacao -> transacao.getProduto()
                        .equals(anuncio));

        if(!transacaoEncontrada) {
            throw new AnuncioNotFound("O anuncio não foi encontrdo nas " +
                    "transações de comprador");
        }

        Avaliacao avaliacao = new Avaliacao(data.getNota(), data.getComentario());

        avaliacao.setAutor(comprador);
        avaliacao.setProduto(anuncio);

        avaliacaoRepository.save(avaliacao);

    }

    public AvaliacaoResponseDTO getAvaliacoes(String id) {

        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(id);

        if(anuncioOptional.isPresent()) {

            Anuncio anuncio = anuncioOptional.get();

            return anuncio.getAvaliacaos();

        } else {
            throw new AnuncioNotFound();
        }
    }


}
