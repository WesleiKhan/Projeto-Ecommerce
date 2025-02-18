package com.example.Ecommerce.saque.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.saque.entity.Saque;
import com.example.Ecommerce.transacoes.entity.Transacao;



@Repository
public interface SaqueRepository extends JpaRepository<Saque, String> {

    Optional<Saque> findByTransacao(Transacao transacao);
}
