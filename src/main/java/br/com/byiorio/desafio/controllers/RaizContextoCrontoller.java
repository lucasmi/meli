package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.ProdutoDetalhadoDTO;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;

@RestController
@RequestMapping(value = "/")
public class RaizContextoCrontoller {
    ProdutoDetalhadoService produtoDetalhadoService;

    public RaizContextoCrontoller(ProdutoDetalhadoService produtoDetalhadoService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
    }

    @GetMapping()
    public ResponseEntity<List<ProdutoDetalhadoDTO>> consultar() {
        return ResponseEntity.ok(produtoDetalhadoService.buscarTodos());
    }

}
