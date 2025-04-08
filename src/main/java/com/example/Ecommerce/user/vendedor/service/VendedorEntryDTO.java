package com.example.Ecommerce.user.vendedor.service;

import com.example.Ecommerce.user.objectValue.Endereco;
import jakarta.validation.constraints.NotBlank;

public class VendedorEntryDTO {

    @NotBlank
    private String cpf;

    private String cnpj;

    @NotBlank
    private String numero_telefone;

    private Endereco endereco;

    @NotBlank
    private String agencia;

    @NotBlank
    private String conta;

    @NotBlank
    private String codigo_banco;


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCodigo_banco() {
        return codigo_banco;
    }

    public void setCodigo_banco(String codigo_banco) {
        this.codigo_banco = codigo_banco;
    }

    
}
