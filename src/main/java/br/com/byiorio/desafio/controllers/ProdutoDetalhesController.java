package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.ProdutoDetalhadoDTO;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;

@RestController
@RequestMapping(value = "/produtos-detalhe")
public class ProdutoDetalhesController {

    ProdutoDetalhadoService produtoDetalhadoService;

    public ProdutoDetalhesController(ProdutoDetalhadoService produtoDetalhadoService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhadoDTO> consultar(@PathVariable String id) {
        return ResponseEntity.ok(produtoDetalhadoService.buscar(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutoDetalhadoDTO>> consultar() {
        return ResponseEntity.ok(produtoDetalhadoService.buscarTodos());
    }

}
