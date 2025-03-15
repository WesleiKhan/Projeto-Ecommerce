package com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs;

import java.math.BigDecimal;

public class AvaliacaoEntryDTO {

    private BigDecimal nota;

    private String comentario;

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
