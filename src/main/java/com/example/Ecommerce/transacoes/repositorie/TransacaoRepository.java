package com.example.Ecommerce.transacoes.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.transacoes.entity.Transacao;
import com.example.Ecommerce.vendedor.entity.Vendedor;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    
    List<Transacao> findByProdutoVendedor(Vendedor vendedor);
}   
