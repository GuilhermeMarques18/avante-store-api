package com.junior.avante_store_api.service;

import com.junior.avante_store_api.dto.CategoriaRequestDTO;
import com.junior.avante_store_api.dto.CategoriaResponseDTO;
import com.junior.avante_store_api.exception.NotFoundException;
import com.junior.avante_store_api.model.Categoria;
import com.junior.avante_store_api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Page<CategoriaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public CategoriaResponseDTO findById(Long id) {
        return repository.findById(id)
                .filter(c -> c.getDeleted() == null)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

    @Transactional
    public CategoriaResponseDTO save(CategoriaRequestDTO request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        return toResponse(repository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO request) {
        Categoria categoria = repository.findById(id)
                .filter(c -> c.getDeleted() == null)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        return toResponse(repository.save(categoria));
    }

    @Transactional
    public void delete(Long id) {
        if (repository.softDeleteById(id, LocalDateTime.now()) == 0) {
            throw new NotFoundException("Categoria não encontrada ou já deletada");
        }
    }

    public Page<CategoriaResponseDTO> searchAdvanced(String nome, Pageable pageable) {
        return repository.searchAdvanced(nome, pageable).map(this::toResponse);
    }

    private CategoriaResponseDTO toResponse(Categoria c) {
        return CategoriaResponseDTO.builder()
                .id(c.getId())
                .nome(c.getNome())
                .descricao(c.getDescricao())
                .build();
    }
}
