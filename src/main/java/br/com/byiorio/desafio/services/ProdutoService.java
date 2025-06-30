package br.com.byiorio.desafio.services;

import java.util.HashSet;
import java.util.LinkedList;
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

        // Já verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Salva Produto
        ProdutoEntity produtoSalvo = produtoRepository.salvar(entidade);

        // Salva Id do produto no usuario
        HashSet<String> produtosCriado = usuarioEncontrado.getIdsProdutos();
        produtosCriado.add(produtoSalvo.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        return produtoSalvo;
    }

    public ProdutoEntity buscar(String id) {
        return produtoRepository.buscar(id, ProdutoEntity.class);
    }

    public List<ProdutoEntity> buscarTodos() {

        // Pega todos os arquivos
        LinkedList<String> todosArquivos = new LinkedList<>(produtoRepository.buscarTodos());

        // Consulta todos os usuarios
        LinkedList<ProdutoEntity> produtos = new LinkedList<>();

        // adiciona usuarios
        todosArquivos.forEach(idProduto -> produtos.add(this.buscar(idProduto)));

        return produtos;

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
        ProdutoEntity produtoEncontrado = produtoRepository.buscar(id, ProdutoEntity.class);

        // Remove vinculo com o usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(produtoEncontrado.getIdUsuario(),
                UsuarioEntity.class);
        usuarioEncontrado.getIdsProdutos().remove(produtoEncontrado.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        // Apaga produto
        produtoRepository.apagar(produtoEncontrado.getId());

    }

}