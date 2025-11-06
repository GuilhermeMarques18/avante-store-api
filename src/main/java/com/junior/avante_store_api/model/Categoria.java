package com.junior.avante_store_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "Descricão não pode estar vazia")
    @Column(columnDefinition = "TEXT")
    private String descricao;
}
