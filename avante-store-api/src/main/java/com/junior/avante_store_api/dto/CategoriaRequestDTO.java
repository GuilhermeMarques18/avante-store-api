package com.junior.avante_store_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {
    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;
}