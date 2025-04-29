package com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs;

import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;

import java.math.BigDecimal;
import java.util.List;

public record AvaliacaoResponseDTO(ResponseSQlAvaliacoes notas,
                                   List<Avaliacao> avaliacaos) {
}
