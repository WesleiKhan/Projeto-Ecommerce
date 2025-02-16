package com.example.Ecommerce.transacoes.entity;

import java.math.BigDecimal;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.comprador.entity.Comprador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Anuncio produto;

    @ManyToOne
    @JoinColumn(name = "comprador_id", referencedColumnName = "id")
    private Comprador comprador;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valor_total;

    public Transacao() {
    }

    public Transacao(int quantidade) {
        this.quantidade = quantidade;
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

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }
    
}
