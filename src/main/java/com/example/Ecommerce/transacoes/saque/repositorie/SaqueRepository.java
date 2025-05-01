package com.example.Ecommerce.transacoes.saque.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.transacoes.saque.entity.Saque;
import com.example.Ecommerce.transacoes.pagamento.entity.Transacao;



@Repository
public interface SaqueRepository extends JpaRepository<Saque, String> {

    Optional<Saque> findByTransacao(Transacao transacao);
}
