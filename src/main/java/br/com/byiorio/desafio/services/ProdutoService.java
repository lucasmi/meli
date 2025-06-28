package br.com.byiorio.desafio.services;

import org.springframework.stereotype.Service;

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

    public ProdutoEntity criar(@Valid ProdutoEntity produto) {

        UsuarioEntity usuario = (UsuarioEntity) usuarioRepository.buscar(produto.getIdUsuario(), UsuarioEntity.class);

        // confirma ID usuario
        produto.setIdUsuario(usuario.getId());

        return (ProdutoEntity) produtoRepository.salvar(produto);
    }

    public ProdutoEntity buscar(String id) {
        return (ProdutoEntity) produtoRepository.buscar(id, ProdutoEntity.class);
    }

    public ProdutoEntity atualizar(String id, ProdutoEntity entidade) {
        return (ProdutoEntity) produtoRepository.salvar(id, entidade);
    }

}