package br.com.byiorio.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/")
    public ResponseEntity<ProdutoEntity> consultar(@RequestParam String id) {
        return ResponseEntity.ok(produtoService.buscar(id));
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoEntity> item(@Valid @RequestBody ProdutoEntity entidade) {
        return ResponseEntity.ok(produtoService.criar(entidade));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoEntity> atualizar(@PathVariable String id, @RequestBody ProdutoEntity entidade) {
        return ResponseEntity.ok(produtoService.atualizar(id, entidade));
    }

}
