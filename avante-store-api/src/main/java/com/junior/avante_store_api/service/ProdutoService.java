package com.junior.avante_store_api.service;

import com.junior.avante_store_api.model.Produto;
import com.junior.avante_store_api.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public Page<Produto> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Produto> findById(Long id){
        return repository.findById(id).filter(p-> p.getDeleted() == null);
    }

    @Transactional
    public Produto save(Produto produto) {
        if (produto.getCategoria() != null && produto.getCategoria().getDeleted() != null) {
            throw new IllegalArgumentException("Categoria está apagada");
        }
        return repository.save(produto);
    }

    @Transactional
    public Produto update(Long id,  Produto produto) {
        Produto exists = findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        exists.setNome(produto.getNome());
        exists.setDescricao(produto.getDescricao());
        exists.setCategoria(produto.getCategoria());
        exists.setPreco(produto.getPreco());
        return repository.save(exists);
    }

    @Transactional
    public void delete(Long id){
        if(repository.softDeleteById(id, LocalDateTime.now())==0){
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public Page<Produto> searchAdvanced (String nome, BigDecimal precoMin, BigDecimal precoMax, Long categoriaId, Pageable pageable) {
        return repository.searchAdvanced(nome, precoMin, precoMax, categoriaId, pageable);
    }

}

