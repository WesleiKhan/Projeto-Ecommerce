package com.example.Ecommerce.saque.service;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaqueEntryDTO {
    
    @JsonProperty("valor")
    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    
}
