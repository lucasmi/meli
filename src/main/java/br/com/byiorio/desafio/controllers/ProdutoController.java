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

import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEntity> consultar(@PathVariable String id) {
        return ResponseEntity.ok(produtoService.buscar(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutoEntity>> consultar() {
        return ResponseEntity.ok(produtoService.buscarTodos());
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoEntity> criar(@Valid @RequestBody ProdutoEntity entidade) {

        ProdutoEntity produtoCriado = produtoService.criar(entidade);
        URI uri = URI.create("/produtos/".concat(produtoCriado.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(produtoCriado);

    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoEntity> atualizar(@PathVariable String id, @RequestBody ProdutoEntity entidade) {
        return ResponseEntity.ok(produtoService.atualizar(id, entidade));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable String id) {
        produtoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
