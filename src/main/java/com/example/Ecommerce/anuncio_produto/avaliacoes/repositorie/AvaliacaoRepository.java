package com.example.Ecommerce.anuncio_produto.avaliacoes.repositorie;

import com.example.Ecommerce.anuncio_produto.avaliacoes.DTOs.ResponseSQlAvaliacoes;
import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, String> {

    List<Avaliacao> findByProduto(Anuncio produto);

    @Query(value = """
            SELECT 
                ROUND(COALESCE(AVG(a.nota),0),2) AS media,
                SUM(CASE WHEN a.nota >= 4 THEN 1 ELSE 0 END) AS positivas,
                SUM(CASE WHEN a.nota < 2 THEN 1 ELSE 0 END) AS negativas,
                SUM(CASE WHEN a.nota >= 2 AND a.nota < 4 THEN 1 ELSE 0 END) AS neutras
            FROM avaliacoes a
            WHERE a.produto_id = :produtoId
                """, nativeQuery = true)
    Optional<ResponseSQlAvaliacoes> findByAvaliacoes(@Param("produtoId") String produtoId);
}
