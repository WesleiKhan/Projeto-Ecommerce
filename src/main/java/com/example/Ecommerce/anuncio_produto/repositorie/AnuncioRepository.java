package com.example.Ecommerce.anuncio_produto.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.anuncio_produto.entity.Anuncio;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, String> {
    
}
