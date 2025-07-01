package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import jakarta.validation.Valid;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoEntity criar(@Valid ProdutoEntity entidade) {
        return produtoRepository.salvar(entidade);
    }

    public ProdutoEntity buscar(String id) {
        return produtoRepository.buscar(id, ProdutoEntity.class);
    }

    public List<ProdutoEntity> buscarTodos() {
        return produtoRepository.buscarTodos(ProdutoEntity.class);
    }

    public ProdutoEntity atualizar(String id, @Valid ProdutoEntity entidade) {
        return produtoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        produtoRepository.apagar(id, ProdutoEntity.class);
    }
}