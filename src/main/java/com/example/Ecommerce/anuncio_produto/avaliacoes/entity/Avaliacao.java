package com.example.Ecommerce.anuncio_produto.avaliacoes.entity;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.comprador.entity.Comprador;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    @JsonIgnore
    private Anuncio produto;

    @ManyToOne
    @JoinColumn(name = "comprador_id", referencedColumnName = "id")
    private Comprador autor;

    @Column(name = "nota", precision = 3, scale = 1)
    private BigDecimal nota;

    @Column(name = "comentario")
    private String comentario;

    public Avaliacao() {

    }

    public Avaliacao(BigDecimal nota, String comentario) {

        if(nota.compareTo(BigDecimal.ZERO) < 0) {
            this.nota = BigDecimal.ZERO;

        } else if (nota.compareTo(BigDecimal.valueOf(5)) > 5) {
            this.nota = BigDecimal.valueOf(5);

        } else {
            this.nota = nota;
        }
        this.comentario = comentario;
    }

    public String getId() {
        return id;
    }

    public Anuncio getProduto() {
        return produto;
    }

    public void setProduto(Anuncio produto) {
        this.produto = produto;
    }

    public Comprador getAutor() {
        return autor;
    }

    public void setAutor(Comprador autor) {
        this.autor = autor;
    }

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
