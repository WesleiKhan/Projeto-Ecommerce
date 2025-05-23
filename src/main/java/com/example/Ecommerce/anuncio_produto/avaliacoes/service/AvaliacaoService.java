package com.example.Ecommerce.anuncio_produto.avaliacoes.service;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoEntryDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoResponseDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.ResponseSQlAvaliacoes;
import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.avaliacoes.repositorie.AvaliacaoRepository;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.anuncio_produto.repositorie.AnuncioRepository;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.comprador.repositorie.CompradorRepository;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final UserService userService;

    private final CompradorRepository compradorRepository;

    private final AnuncioRepository anuncioRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            UserService userService,
                            CompradorRepository compradorRepository,
                            AnuncioRepository anuncioRepository) {

        this.avaliacaoRepository = avaliacaoRepository;
        this.userService = userService;
        this.compradorRepository = compradorRepository;
        this.anuncioRepository = anuncioRepository;
    }

    public void register(String id, AvaliacaoEntryDTO data) {

        User user = userService.getLoggedInUser();

        Comprador comprador = compradorRepository.findByNome(user)
                        .orElseThrow(() -> new UserNotFound("Comprador não " +
                                "foi encontrado"));

        Anuncio anuncio = anuncioRepository.findById(id)
                        .orElseThrow(AnuncioNotFound::new);

        if(!comprador.transacaoExiste(anuncio)) {
            throw new AnuncioNotFound("Você não pode avaliar um anuncio " +
                    "de um produto que você não fez a compra.");
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByProduto(anuncio);

        boolean avaliacaoEncontrada = avaliacoes.stream()
                .anyMatch(avaliacao -> avaliacao.authorEquals(comprador));

        if(avaliacaoEncontrada) {
            throw new UserNotAutorization("Você ja avaliou esse anuncio.");
        }

        Avaliacao avaliacao = new Avaliacao(data.getNota(), data.getComentario());

        avaliacao.setAutor(comprador);
        avaliacao.setProduto(anuncio);

        avaliacaoRepository.save(avaliacao);

    }


    public AvaliacaoResponseDTO seeAvaliacoes(String id) {

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(AnuncioNotFound::new);

        ResponseSQlAvaliacoes response = avaliacaoRepository.findByAvaliacoes(id)
                .orElseThrow(() -> new RuntimeException("erro ao obter " +
                        "avaliações do produto."));

        return new AvaliacaoResponseDTO(response, anuncio.getAvaliacaos());

    }


}
