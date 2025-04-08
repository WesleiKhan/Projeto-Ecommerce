package com.example.Ecommerce.user.objectValue;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String rua;

    private String numero;

    private String cidade;

    private String estado;

    private String cep;


    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        if(rua != null && !rua.trim().isEmpty()) {
            this.rua = rua;
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if(numero != null && !numero.trim().isEmpty()) {
            this.numero = numero;
        }
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if(cidade != null && !cidade.trim().isEmpty()) {
            this.cidade = cidade;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if(estado != null && !estado.trim().isEmpty()) {
            this.estado = estado;
        }
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep != null && !cep.trim().isEmpty()) {
            this.cep = cep;
        }
    }
}
