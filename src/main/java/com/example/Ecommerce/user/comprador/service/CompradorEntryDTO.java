package com.example.Ecommerce.user.comprador.service;

import com.example.Ecommerce.user.objectValue.Endereco;

public class CompradorEntryDTO {
    
    private String cpf;

    private String numero_telefone;

    private Endereco endereco;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumero_telefone() {
        return numero_telefone;
    }

    public void setNumero_telefone(String numero_telefone) {
        this.numero_telefone = numero_telefone;
    }

    public Endereco getEndereco() {

        return endereco;
    }

    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;
    }

    
}
