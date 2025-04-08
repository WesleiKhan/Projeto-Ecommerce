package com.example.Ecommerce.user.vendedor.service;

import com.example.Ecommerce.user.objectValue.Endereco;

public class VendedorEntryEditDTO {

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
