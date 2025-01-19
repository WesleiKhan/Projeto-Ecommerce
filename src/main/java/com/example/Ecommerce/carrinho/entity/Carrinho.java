package com.example.Ecommerce.carrinho.entity;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import com.example.Ecommerce.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Anuncio anuncio;

    @Column(name = "quantidade")
    private Integer quantidade = 1;


    public Carrinho() {
    }

    public Carrinho(Integer quantidade) {

        if (quantidade != null && quantidade > this.quantidade) {

            this.quantidade = quantidade;

        } else {
            this.quantidade = 1;
        }
        
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
  
}
