package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.exceptions.NegocialException;
import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
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

    public ProdutoEntity atualizar(String id, ProdutoEntity entidade) {

        // verifica se produto existe
        ProdutoEntity produtoEncontrado = produtoRepository.buscar(id,
                ProdutoEntity.class);

        // Ja verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Não deixa trocar de usuario durante atualizacao
        if (!usuarioEncontrado.getId().equals(produtoEncontrado.getIdUsuario())) {
            throw new NegocialException("Usuario nao pode ser alterado do produto");
        }

        return produtoRepository.salvar(produtoEncontrado.getId(), entidade);
    }

    public void apagar(String id) {
        produtoRepository.apagar(id, ProdutoEntity.class);
    }
}