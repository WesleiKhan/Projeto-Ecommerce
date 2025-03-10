package com.example.Ecommerce.comprador.entity;

import java.util.List;

import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "compradores")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "nome_id", referencedColumnName = "id")
    private User nome;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "numero_telefone")
    private String numero_telefone;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cep")
    private String cep;

    @Column(name = "transacoes_id")
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transacao> transacoes;


    public Comprador() {

    }

    public Comprador(String cpf, String numero_telefone, String rua, String numero, String cidade, String estado, String cep) {

        this.cpf = cpf;
        this.numero_telefone = numero_telefone;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getId() {

        return id;
    }

    public void setNome(User nome) {

        this.nome = nome;
    }

    public User getNome() {

        return nome;
    }
    
    public void setCpf(String cpf) {

        this.cpf = cpf;
    }

    public String getCpf() {

        return cpf;
    }

    public void setNumero_telefone(String numero_telefone) {
        if(numero_telefone != null && !numero_telefone.trim().isEmpty()) {
            this.numero_telefone = numero_telefone;
        }
    }

    public String getNumero_telefone() {

        return numero_telefone;
    }

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
        if(cep != null && !cep.trim().isEmpty()) {
            this.cep = cep;
        }
    }

}
