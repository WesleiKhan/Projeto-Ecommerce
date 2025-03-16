package com.example.Ecommerce.anuncio_produto.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.AvaliacaoResponseDTO;
import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.carrinho.entity.Carrinho;
import com.example.Ecommerce.favorito.entity.Favorito;
import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.vendedor.entity.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "anuncios")
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Lob
    @Column(name = "imagem")
    private String imagem;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "quantidade_produto")
    private Integer quantidade_produto;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Vendedor vendedor;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "largura")
    private Double largura;

    @Column(name = "comprimento")
    private Double comprimento;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "favoritos_id")
    @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Favorito> favoritos;

    @Column(name = "carrinhos_id")
    @OneToMany(mappedBy = "anuncio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Carrinho> carrinhos;

    @Column(name = "transacoes_id")
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transacao> transacoes;

    @Column(name = "avaliacoes_id")
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Avaliacao> avaliacaos;


    public Anuncio() {
    }


    public Anuncio(String titulo, String descricao, String imagem,
                   BigDecimal valor, Integer quantidade_produto, Double altura,
                   Double largura, Double comprimento, Double peso) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.valor = valor;
        this.quantidade_produto = quantidade_produto;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.peso = peso;
    }


    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao != null && !descricao.trim().isEmpty()) {
            this.descricao = descricao;
        }
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getValor() {

        return valor;
    }

    public void setValor(BigDecimal valor) {
        if(valor != null) {
            this.valor = valor;
        }
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        if(altura != null) {
            this.altura = altura;
        }
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        if(largura != null) {
            this.largura = largura;
        }
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        if(comprimento != null) {
            this.comprimento = comprimento;
        }
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        if(peso != null) {
            this.peso = peso;
        }
    }

    public Integer getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(Integer quantidade_produto) {
        if(quantidade_produto != null) {
            this.quantidade_produto = quantidade_produto;
        }
    }

    public AvaliacaoResponseDTO getAvaliacaos() {

        BigDecimal notas = BigDecimal.ZERO;
        Integer avaliacoesPositivas = 0;
        Integer avaliacoesNegativas = 0;
        Integer avaliacoesNeutras = 0;

        for(int i = 0; i < avaliacaos.size(); i++) {

            notas = notas.add(avaliacaos.get(i).getNota());

            if(avaliacaos.get(i).getNota().compareTo(BigDecimal.valueOf(4))
                    >= 0) {
                avaliacoesPositivas++;

            } else if(avaliacaos.get(i).getNota().compareTo(BigDecimal.valueOf(2))
                    < 0) {
                avaliacoesNegativas++;

            } else {
                avaliacoesNeutras++;
            }
        }

        BigDecimal mediaNotas =
                notas.divide(BigDecimal.valueOf(avaliacaos.size()), 2,
                        RoundingMode.HALF_UP);

        if(mediaNotas.compareTo(BigDecimal.ZERO) < 0) {
            mediaNotas = BigDecimal.ZERO;

        } else if (mediaNotas.compareTo(BigDecimal.valueOf(5)) > 5) {
            mediaNotas = BigDecimal.valueOf(5);
        }
        return new AvaliacaoResponseDTO(mediaNotas,
                avaliacoesPositivas,
                avaliacoesNegativas,
                avaliacoesNeutras,
                avaliacaos);
    }
}
