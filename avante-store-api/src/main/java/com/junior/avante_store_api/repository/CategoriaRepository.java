package com.junior.avante_store_api.repository;

import com.junior.avante_store_api.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {


    @Override
    @Query("SELECT c FROM Categoria c WHERE c.deleted IS NULL")
    Page<Categoria> findAll(Pageable pageable);

    @Query("SELECT c FROM Categoria c WHERE " +
            "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "c.deleted IS NULL")
    Page<Categoria> searchAdvanced(@Param("nome") String nome, Pageable pageable);


    @Modifying
    @Query("UPDATE Categoria c SET c.deleted = :now WHERE c.id = :id AND c.deleted IS NULL")
    int softDeleteById(@Param("id") Long id, @Param("now") LocalDateTime now);

}
