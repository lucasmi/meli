package br.com.byiorio.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.ProdutosDTO;
import br.com.byiorio.desafio.services.ProdutoService;

@RestController(value = "/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/")
    public ProdutosDTO consultar() {
        return produtoService.consultar();
    }

}
