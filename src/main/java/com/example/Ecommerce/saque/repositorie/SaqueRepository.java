package com.example.Ecommerce.saque.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.saque.entity.Saque;

@Repository
public interface SaqueRepository extends JpaRepository<Saque, String> {
    
}
