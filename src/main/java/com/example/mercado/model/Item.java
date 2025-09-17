package com.example.mercado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres.")
    private String nome;

    @NotNull(message = "Tipo é obrigatório.")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;


    @NotNull(message = "Raridade é obrigatória.")
    @Enumerated(EnumType.STRING)
    private Raridade raridade;

    @Min(value = 0, message = "Preço deve ser um valor positivo.")
    private double preco;

    @ManyToOne
    private Personagem personagem;


}
