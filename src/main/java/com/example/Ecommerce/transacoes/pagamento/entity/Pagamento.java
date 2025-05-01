package com.example.Ecommerce.transacoes.pagamento.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.transacoes.saque.entity.Saque;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Pagamento {

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

    @Column(name = "data_da_compra", nullable = false, updatable = false)
    private LocalDateTime data_da_compra = LocalDateTime.now();

    @OneToOne(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Saque saque;

    @Column(name = "id_charge_stripe")
    private String id_charge_stripe;

    public Pagamento() {
    }

    public Pagamento(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getData_da_compra() {
        return data_da_compra;
    }

    public String getId_charge_stripe() {
        return id_charge_stripe;
    }

    public void setId_charge_stripe(String id_charge_stripe) {
        this.id_charge_stripe = id_charge_stripe;
    }

    public boolean produtoEquals(Anuncio produto) {

        return this.produto.anuncioEqualsId(produto);
    }
    
}
