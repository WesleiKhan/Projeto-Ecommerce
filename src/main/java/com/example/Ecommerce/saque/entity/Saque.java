package com.example.Ecommerce.saque.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.Ecommerce.vendedor.entity.Vendedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "saques")
public class Saque {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sacador_id", referencedColumnName = "id")
    private Vendedor sacador;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_do_saque", nullable = false, updatable = false)
    private LocalDateTime data_do_saque = LocalDateTime.now();

    public Saque() {
    }

    public Saque(BigDecimal valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public Vendedor getSacador() {
        return sacador;
    }

    public void setSacador(Vendedor sacador) {
        this.sacador = sacador;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    
}
