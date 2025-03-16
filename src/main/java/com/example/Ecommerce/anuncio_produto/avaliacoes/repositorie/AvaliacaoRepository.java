package com.example.Ecommerce.anuncio_produto.avaliacoes.repositorie;

import com.example.Ecommerce.anuncio_produto.avaliacoes.entity.Avaliacao;
import com.example.Ecommerce.anuncio_produto.entity.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, String> {

    List<Avaliacao> findByProduto(Anuncio produto);
}
