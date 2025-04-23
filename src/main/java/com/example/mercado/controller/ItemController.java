package com.example.mercado.controller;

import com.example.mercado.model.Item;
import com.example.mercado.model.Raridade;
import com.example.mercado.model.Tipo;
import com.example.mercado.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping

    public ResponseEntity<Item> criarItem(@Valid @RequestBody Item item) {

        Item novoItem = itemRepository.save(item);

        return ResponseEntity.status(201).body(novoItem);

    }

    @GetMapping

    public List<Item> obterTodosItens() {

        return itemRepository.findAll();

    }

    @GetMapping("/{id}")

    public ResponseEntity<Item> obterItemPorId(@PathVariable Long id) {

        Optional<Item> item = itemRepository.findById(id);

        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")

    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @Valid @RequestBody Item item) {

        if (itemRepository.existsById(id)) {

            item.setId(id);

            Item itemAtualizado = itemRepository.save(item);

            return ResponseEntity.ok(itemAtualizado);

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> excluirItem(@PathVariable Long id) {

        if (itemRepository.existsById(id)) {

            itemRepository.deleteById(id);

            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @GetMapping("/buscarPorNome")

    public ResponseEntity<List<Item>> buscarItensPorNome(@RequestParam String nome) {

        List<Item> itens = itemRepository.findByNomeContainingIgnoreCase(nome);

        if (itens.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(itens);

    }

    @GetMapping("/buscarPorTipo")

    public ResponseEntity<List<Item>> buscarItensPorTipo(@RequestParam Tipo tipo) {

        List<Item> itens = itemRepository.findByTipo(tipo);

        if (itens.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(itens);

    }

    @GetMapping("/buscarPorPreco")

    public ResponseEntity<List<Item>> buscarItensPorPreco(

            @RequestParam double precoMinimo,

            @RequestParam double precoMaximo) {

        List<Item> itens = itemRepository.findByPrecoBetween(precoMinimo, precoMaximo);

        if (itens.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(itens);

    }

    @GetMapping("/buscarPorRaridade")

    public ResponseEntity<List<Item>> buscarItensPorRaridade(@RequestParam Raridade raridade) {

        List<Item> itens = itemRepository.findByRaridade(raridade);

        if (itens.isEmpty()) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(itens);

    }

}
