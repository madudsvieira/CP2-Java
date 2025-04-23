package com.example.mercado.repository;


import com.example.mercado.model.Classe;
import com.example.mercado.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    List<Personagem> findByNomeIgnoreCase(String nome);
    List<Personagem> findByClasse(Classe classe);




}
