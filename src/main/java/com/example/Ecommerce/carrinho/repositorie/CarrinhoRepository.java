package com.example.Ecommerce.carrinho.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.carrinho.entity.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {
    
}
