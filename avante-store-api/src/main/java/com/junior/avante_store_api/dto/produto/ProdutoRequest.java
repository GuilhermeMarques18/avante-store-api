package com.junior.avante_store_api.dto.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequest {

    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 255, message = "Nome não pode ultrapassar 255 caracteres")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "Preço não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "Categoria não pode ser nula")
    private Long categoriaId;
}
