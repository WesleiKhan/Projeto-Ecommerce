package com.example.Ecommerce.comprador.entity;

import com.example.Ecommerce.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "cpf")
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

        this.numero_telefone = numero_telefone;
    }

    public String getNumero_telefone() {

        return numero_telefone;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
