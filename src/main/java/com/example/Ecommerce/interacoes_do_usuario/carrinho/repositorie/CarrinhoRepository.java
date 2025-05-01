package com.example.Ecommerce.interacoes_do_usuario.carrinho.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.interacoes_do_usuario.carrinho.entity.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {
    
}
