package com.example.Ecommerce.anuncio_produto.entity;

import java.math.BigDecimal;
import java.util.List;

import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.service.AnuncioEntryDTO;
import com.example.Ecommerce.interacoes_do_usuario.carrinho.entity.Carrinho;
import com.example.Ecommerce.interacoes_do_usuario.favorito.entity.Favorito;
import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;
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
    private List<Pagamento> transacoes;

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

    public void setId(String id) {
        this.id = id;
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
        if(imagem != null && !imagem.trim().isEmpty()) {
            this.imagem = imagem;
        }
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

    public List<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public boolean vendedorEquals(Vendedor vendedorLogado) {

        return this.vendedor.idEquals(vendedor);
    }

    public void atualizarDados(AnuncioEntryDTO data, String newImagem ) {

        this.setTitulo(data.getTitulo());
        this.setImagem(newImagem);
        this.setDescricao(data.getDescricao());
        this.setValor(data.getValor());
        this.setQuantidade_produto(data.getQuantidade());
        this.setAltura(data.getAltura());
        this.setLargura(data.getLargura());
        this.setComprimento(data.getComprimento());
        this.setPeso(data.getPeso());
    }

    public boolean anuncioEqualsId(Anuncio produto) {

        return this.id.equals(produto.getId());
    }
}
