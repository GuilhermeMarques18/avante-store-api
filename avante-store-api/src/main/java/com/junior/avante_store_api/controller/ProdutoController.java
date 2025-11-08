package com.junior.avante_store_api.controller;

import com.junior.avante_store_api.model.Produto;
import com.junior.avante_store_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<Page<Produto>>list(Pageable pageable){
        Page<Produto> produtos = service.list(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        return service.findById(id)
                .map(ResponseEntity:: ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/busca")
    public ResponseEntity<Page<Produto>>searchAdvanced (
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Long categoriaId,
            Pageable pageable){
        Page<Produto> result = service.searchAdvanced(nome, precoMin, precoMax, categoriaId, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Produto> save( @Valid @RequestBody Produto produto){
        Produto save = service.save(produto);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody Produto produto){
        Produto update = service.update(id, produto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
