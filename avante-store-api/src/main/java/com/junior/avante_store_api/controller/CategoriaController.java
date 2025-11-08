package com.junior.avante_store_api.controller;

import com.junior.avante_store_api.model.Categoria;
import com.junior.avante_store_api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    @GetMapping
    public ResponseEntity<Page<Categoria>> list(Pageable pageable) {
        Page<Categoria> categorias = service.list(pageable);
        return ResponseEntity.ok(categorias);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria) {
        Categoria salva = service.save(categoria);
        return ResponseEntity.ok(salva);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        Categoria atualizada = service.update(id, categoria);
        return ResponseEntity.ok(atualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<Categoria>> searchAdvanced(@RequestParam(required = false) String nome, Pageable pageable) {
        Page<Categoria> resultados = service.searchAdvanced(nome, pageable);
        return ResponseEntity.ok(resultados);
    }
}
