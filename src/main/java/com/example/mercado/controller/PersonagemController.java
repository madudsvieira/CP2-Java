package com.example.mercado.controller;

import com.example.mercado.model.Classe;
import com.example.mercado.model.Personagem;
import com.example.mercado.repository.PersonagemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {

    @Autowired
    private PersonagemRepository personagemRepository;
    @PostMapping
    public ResponseEntity<Personagem> criarPersonagem(@Valid @RequestBody Personagem personagem, BindingResult result) {
        Personagem novoPersonagem = personagemRepository.save(personagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPersonagem);
    }

    @GetMapping
    public List<Personagem> obterTodosPersonagens() {
        return personagemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personagem> obterPersonagemPorId(@PathVariable Long id) {
        Optional<Personagem> personagem = personagemRepository.findById(id);
        if (personagem.isPresent()) {
            return ResponseEntity.ok(personagem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personagem> atualizarPersonagem(@PathVariable Long id, @Valid @RequestBody Personagem personagem) {
        if (personagemRepository.existsById(id)) {
            personagem.setId(id);
            Personagem personagemAtualizado = personagemRepository.save(personagem);
            return ResponseEntity.ok(personagemAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPersonagem(@PathVariable Long id) {
        if (personagemRepository.existsById(id)) {
            personagemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Personagem>> buscarPersonagensPorNome(@RequestParam String nome) {
        List<Personagem> personagens = personagemRepository.findByNomeIgnoreCase(nome);
        if (personagens.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personagens);
    }

    @GetMapping("/buscarPorClasse")
    public ResponseEntity<List<Personagem>> buscarPersonagensPorClasse(@RequestParam Classe classe) {
        List<Personagem> personagens = personagemRepository.findByClasse(classe);
        if (personagens.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personagens);
    }
}
