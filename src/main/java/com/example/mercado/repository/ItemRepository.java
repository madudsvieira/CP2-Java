package com.example.mercado.repository;

import com.example.mercado.model.Item;
import com.example.mercado.model.Raridade;
import com.example.mercado.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository <Item, Long> {
    List<Item> findByNomeContainingIgnoreCase(String nome);
    List<Item> findByTipo(Tipo tipo);
    List<Item> findByPrecoBetween(double precoMinimo, double precoMaximo);
    List<Item> findByRaridade(Raridade raridade);
}
