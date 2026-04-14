package com.junior.avante_store_api.service;

import com.junior.avante_store_api.dto.produto.ProdutoRequest;
import com.junior.avante_store_api.dto.produto.ProdutoResponse;
import com.junior.avante_store_api.exception.NotFoundException;
import com.junior.avante_store_api.model.Categoria;
import com.junior.avante_store_api.model.Produto;
import com.junior.avante_store_api.repository.CategoriaRepository;
import com.junior.avante_store_api.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public Page<ProdutoResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ProdutoResponse findById(Long id) {
        return repository.findById(id)
                .filter(p -> p.getDeleted() == null)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoResponse save(ProdutoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .filter(c -> c.getDeleted() == null)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada ou inativa"));

        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoria);

        return toResponse(repository.save(produto));
    }

    @Transactional
    public ProdutoResponse update(Long id, ProdutoRequest request) {
        Produto produto = repository.findById(id)
                .filter(p -> p.getDeleted() == null)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .filter(c -> c.getDeleted() == null)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada ou inativa"));

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setCategoria(categoria);

        return toResponse(repository.save(produto));
    }

    @Transactional
    public void delete(Long id) {
        if (repository.softDeleteById(id, LocalDateTime.now()) == 0) {
            throw new NotFoundException("Produto não encontrado ou já deletado");
        }
    }

    public Page<ProdutoResponse> searchAdvanced(String nome, BigDecimal precoMin,
                                                BigDecimal precoMax, Long categoriaId,
                                                Pageable pageable) {
        return repository.searchAdvanced(nome, precoMin, precoMax, categoriaId, pageable)
                .map(this::toResponse);
    }

    private ProdutoResponse toResponse(Produto p) {
        return ProdutoResponse.builder()
                .id(p.getId())
                .nome(p.getNome())
                .descricao(p.getDescricao())
                .preco(p.getPreco())
                .categoriaId(p.getCategoria().getId())
                .categoriaNome(p.getCategoria().getNome())
                .build();
    }
}