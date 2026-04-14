package com.junior.avante_store_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
}
