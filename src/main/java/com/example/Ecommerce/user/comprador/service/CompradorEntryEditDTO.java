package com.example.Ecommerce.user.comprador.service;

import com.example.Ecommerce.user.objectValue.Endereco;

public class CompradorEntryEditDTO {

    private String numeroTelefone;

    private Endereco endereco;

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public Endereco getEndereco() {

        return endereco;
    }

    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;
    }
}
