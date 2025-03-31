package com.example.Ecommerce.user.comprador.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.user.comprador.entity.Comprador;
import com.example.Ecommerce.user.entity.User;


@Repository
public interface CompradorRepository extends JpaRepository<Comprador, String> {
    
    Optional<Comprador> findByNome(User nome);
}
