package com.example.mercado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Enumerated(EnumType.STRING)
    private Classe classe;

    @NotNull
    @Min(value = 1, message = "O valor mínimo deve ser 1")
    @Max(value = 99, message = "O valor máximo deve ser 99")
    private int nivel;

    private double moedas;





}
