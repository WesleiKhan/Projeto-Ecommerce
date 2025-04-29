package com.example.Ecommerce.user.comprador.entity;

import java.util.List;

import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.user.comprador.service.CompradorEntryEditDTO;
import com.example.Ecommerce.user.entity.User;
import com.example.Ecommerce.user.objectValue.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

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

    @Embedded
    private Endereco endereco;

    @Column(name = "transacoes_id")
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transacao> transacoes;

    @Column(name = "avaliacoes_id")
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Avaliacao> avaliacaos;


    public Comprador() {

    }

    public Comprador(String cpf, String numero_telefone,
                     Endereco endereco) {

        this.cpf = cpf;
        this.numero_telefone = numero_telefone;
        this.endereco = endereco;
    }

    public void setId(String id) {

        this.id = id;
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

    public Endereco getEndereco() {

        return endereco;
    }

    public void setEndereco(Endereco endereco) {

        this.endereco = endereco;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void atualizarDados(CompradorEntryEditDTO data) {

        setNumero_telefone(data.getNumeroTelefone());
        this.endereco.setRua(data.getEndereco().getRua());
        this.endereco.setNumero(data.getEndereco().getNumero());
        this.endereco.setCidade(data.getEndereco().getCidade());
        this.endereco.setEstado(data.getEndereco().getEstado());
        this.endereco.setCep(data.getEndereco().getCep());
    }

    public boolean compradorEqualsId(Comprador comprador) {

        return this.id.equals(comprador.getId());
    }

    public boolean transacaoExiste(Anuncio anuncio) {

        return this.transacoes.stream().anyMatch(
                trasacao -> trasacao.produtoEquals(anuncio)
        );
    }
}
