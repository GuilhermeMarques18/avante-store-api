package com.junior.avante_store_api.repository;

import com.junior.avante_store_api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Override
    @Query("SELECT p FROM Produto p WHERE p.deleted IS NULL")
    Page<Produto> findAll(Pageable pageable);

    @Query("SELECT p FROM Produto p WHERE " +
            "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:precoMin IS NULL OR p.preco >= :precoMin) AND " +
            "(:precoMax IS NULL OR p.preco <= :precoMax) AND " +
            "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
            "p.deleted IS NULL")
    Page<Produto> searchAdvanced(@Param("nome") String nome,
                                @Param("precoMin") BigDecimal precoMin,
                                @Param("precoMax") BigDecimal precoMax,
                                @Param("categoriaId") Long categoriaId,
                                Pageable pageable);

    @Modifying
    @Query("UPDATE Produto p SET p.deleted = :now WHERE p.id = :id AND p.deleted IS NULL")
    int softDeleteById(@Param("id") Long id, @Param("now") LocalDateTime now);

}