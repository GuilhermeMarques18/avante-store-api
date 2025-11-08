package com.junior.avante_store_api.service;

import com.junior.avante_store_api.model.Categoria;
import com.junior.avante_store_api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;


    public Page<Categoria> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Categoria> findById(Long id) {
        return repository.findById(id).filter(c -> c.getDeleted() == null);
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria exist = findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        exist.setNome(categoriaAtualizada.getNome());
        exist.setDescricao(categoriaAtualizada.getDescricao());
        return repository.save(exist);
    }

    @Transactional
    public void delete(Long id) {
        if (repository.softDeleteById(id, LocalDateTime.now()) == 0) {
            throw new RuntimeException("Categoria não encontrada ou já deletada");
        }
    }

    public Page<Categoria> searchAdvanced(String nome, Pageable pageable) {
        return repository.searchAdvanced(nome, pageable);
    }
}
