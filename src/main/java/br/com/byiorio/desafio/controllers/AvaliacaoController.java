package br.com.byiorio.desafio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.services.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/avaliacoes")
public class AvaliacaoController {

    AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoEntity> consultar(@PathVariable String id) {
        return ResponseEntity.ok(avaliacaoService.buscar(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<AvaliacaoEntity>> consultar() {
        return ResponseEntity.ok(avaliacaoService.buscarTodos());
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvaliacaoEntity> criar(@Valid @RequestBody AvaliacaoEntity entidade) {
        AvaliacaoEntity usuarioCriado = avaliacaoService.criar(entidade);
        URI uri = URI.create("/avaliacoes/".concat(usuarioCriado.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(usuarioCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<AvaliacaoEntity> atualizar(@PathVariable String id,
            @Valid @RequestBody AvaliacaoEntity entidade) {
        return ResponseEntity.ok(avaliacaoService.atualizar(id, entidade));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable String id) {
        avaliacaoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
