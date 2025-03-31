package com.example.Ecommerce.user.vendedor.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.user.vendedor.entity.Vendedor;
import com.example.Ecommerce.user.entity.User;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, String> {

    Optional<Vendedor> findByNome(User nome);
    
}
