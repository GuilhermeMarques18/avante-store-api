package com.junior.avante_store_api.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 255, message ="Nome não pode ultrapassar 255 caracteres")
    private String nome;

    @NotBlank(message = "Descrição não pode estar vazia")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn (name = "categoria_id")
    @NotNull(message = "Categoria não pode ser nula")
    private Categoria categoria;

}
