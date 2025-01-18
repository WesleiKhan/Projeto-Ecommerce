package com.example.Ecommerce.vendedor.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.vendedor.entity.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, String> {
    
}
