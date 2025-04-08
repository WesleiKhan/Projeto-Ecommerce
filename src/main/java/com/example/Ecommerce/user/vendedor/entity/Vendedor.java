package com.example.Ecommerce.user.vendedor.entity;

import java.util.List;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.objectValue.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

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

    @Embedded
    private Endereco endereco;

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
                    Endereco endereco) {

        this.cpf = cpf;
        this.cnpj = (cnpj == null || cnpj.trim().isEmpty()) ? null : cnpj;
        this.numero_telefone = numero_telefone;
        this.endereco = endereco;

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
        this.cnpj = (cnpj == null || cnpj.trim().isEmpty()) ? null : cnpj;
    }

    public String getNumero_telefone() {
        return numero_telefone;
    }

    public void setNumero_telefone(String numero_telefone) {
        if(numero_telefone != null && !numero_telefone.trim().isEmpty()) {
            this.numero_telefone = numero_telefone;
        }
    }

    public Endereco getEndereco() {

        return endereco;
    }

    public void setEndereco(Endereco endereco) {

        this.endereco.setRua(endereco.getRua());
        this.endereco.setNumero(endereco.getNumero());
        this.endereco.setCidade(endereco.getCidade());
        this.endereco.setEstado(endereco.getEstado());
        this.endereco.setCep(endereco.getCep());
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
