package com.example.Ecommerce.favorito.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.favorito.entity.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, String>{
    
}
