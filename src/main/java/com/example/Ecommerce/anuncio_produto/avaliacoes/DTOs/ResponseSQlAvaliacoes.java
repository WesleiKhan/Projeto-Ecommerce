package com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs;

import java.math.BigDecimal;

public record ResponseSQlAvaliacoes(
        BigDecimal media,
        BigDecimal positivas,
        BigDecimal negativas,
        BigDecimal neutras
) {
}
