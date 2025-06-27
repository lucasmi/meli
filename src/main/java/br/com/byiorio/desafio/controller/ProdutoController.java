package br.com.byiorio.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.model.ProdutosDTO;
import br.com.byiorio.desafio.service.ProdutoService;

@RestController(value = "/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/")
    public ProdutosDTO consultar() {
        return produtoService.consultar();
    }

}
