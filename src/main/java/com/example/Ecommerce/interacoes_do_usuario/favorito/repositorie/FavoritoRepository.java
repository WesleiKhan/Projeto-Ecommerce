package com.example.Ecommerce.interacoes_do_usuario.favorito.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.interacoes_do_usuario.favorito.entity.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, String>{
    
}
