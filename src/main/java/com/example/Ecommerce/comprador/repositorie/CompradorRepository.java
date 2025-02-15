package com.example.Ecommerce.comprador.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.comprador.entity.Comprador;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, String> {
    
}
