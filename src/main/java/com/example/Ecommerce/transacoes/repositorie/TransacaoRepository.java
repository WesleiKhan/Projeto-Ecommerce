package com.example.Ecommerce.transacoes.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.transacoes.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    
}
