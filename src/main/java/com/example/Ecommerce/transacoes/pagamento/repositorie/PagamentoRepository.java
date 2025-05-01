package com.example.Ecommerce.transacoes.pagamento.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ecommerce.transacoes.pagamento.entity.Pagamento;
import com.example.Ecommerce.user.vendedor.entity.Vendedor;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
    
    List<Pagamento> findByProdutoVendedor(Vendedor vendedor);
}   
