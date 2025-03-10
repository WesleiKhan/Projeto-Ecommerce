package com.example.Ecommerce.vendedor.entity;

import java.util.List;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.saque.entity.Saque;
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
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "nome_id", referencedColumnName = "id")
    private User nome;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "cnpj", unique = true)
    private String cnpj;

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

    @Column(name = "anuncios_id")
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Anuncio> anuncios;

    @Column(name = "saques_id")
    @OneToMany(mappedBy = "sacador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Saque> saques;

    @Column(name = "id_account_stripe")
    private String id_account_stripe;
    

    public Vendedor() {
    }


    public Vendedor(String cpf, String cnpj, String numero_telefone,
                    String rua, String numero, String cidade,
                    String estado, String cep) {

        this.cpf = cpf;
        this.cnpj = cnpj;
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

    public User getNome() {
        return nome;
    }

    public void setNome(User nome) {
        this.nome = nome;
    }

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



    public List<Anuncio> getAnuncios() {
        return anuncios;
    }


    public List<Saque> getSaques() {
        return saques;
    }


    public String getId_account_stripe() {
        return id_account_stripe;
    }


    public void setId_account_stripe(String id_account_stripe) {
        this.id_account_stripe = id_account_stripe;
    }

    
}
