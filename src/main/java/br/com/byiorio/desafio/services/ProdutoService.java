package br.com.byiorio.desafio.services;

import java.util.HashSet;

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

        // Já verifica se existe usuario
        UsuarioEntity usuarioEncontrado = (UsuarioEntity) usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Salva Produto
        ProdutoEntity produtoSalvo = (ProdutoEntity) produtoRepository.salvar(entidade);

        // Salva Id do produto no usuario
        HashSet<String> produtosCriado = usuarioEncontrado.getIdsProdutos();
        produtosCriado.add(produtoSalvo.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        return produtoSalvo;
    }

    public ProdutoEntity buscar(String id) {
        return (ProdutoEntity) produtoRepository.buscar(id, ProdutoEntity.class);
    }

    public ProdutoEntity atualizar(String id, ProdutoEntity entidade) {

        // Ja verifica se existe usuario
        UsuarioEntity usuarioEncontrado = (UsuarioEntity) usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Não deixa trocar de usuario durante atualizacao
        if (!usuarioEncontrado.getId().equals(entidade.getIdUsuario())) {
            throw new NegocialException("Usuario nao pode ser alterado do produto");
        }

        return (ProdutoEntity) produtoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        ProdutoEntity produtoEncontrado = (ProdutoEntity) produtoRepository.buscar(id, ProdutoEntity.class);

        // Remove vinculo com o usuario
        UsuarioEntity usuarioEncontrado = (UsuarioEntity) usuarioRepository.buscar(produtoEncontrado.getIdUsuario(),
                UsuarioEntity.class);
        usuarioEncontrado.getIdsProdutos().remove(produtoEncontrado.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        // Apaga produto
        produtoRepository.apagar(produtoEncontrado.getId());

    }

}