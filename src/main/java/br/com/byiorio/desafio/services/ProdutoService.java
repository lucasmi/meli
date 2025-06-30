package br.com.byiorio.desafio.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.exceptions.NegocialException;
import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private AvaliacaoRepository avaliacaoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository,
            AvaliacaoRepository avaliacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
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
        // Verifica se produto existe
        ProdutoEntity produtoEncontrado = produtoRepository.buscar(id, ProdutoEntity.class);

        // Remove vinculo do produto com o usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(produtoEncontrado.getIdUsuario(),
                UsuarioEntity.class);

        // Remove vinculo do produto com o usuario
        usuarioEncontrado.getIdsProdutos().remove(produtoEncontrado.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        // Remover avaliacoes relacionadas ao produto
        produtoEncontrado.getIdsAvaliacoes().forEach(idAvaliacao -> {
            // Encontra avaliacao para remover
            AvaliacaoEntity avaliacaoEncontrada = avaliacaoRepository.buscar(idAvaliacao, AvaliacaoEntity.class);

            // Encontra qual usuario fez a avaliacao e remove
            UsuarioEntity comentarista = usuarioRepository.buscar(avaliacaoEncontrada.getIdUsuario(),
                    UsuarioEntity.class);
            comentarista.getIdsAvaliacoes().remove(idAvaliacao);
            usuarioRepository.salvar(comentarista);

            // Remove avaliacao
            avaliacaoRepository.apagar(idAvaliacao);
        });

        // Apaga produto
        produtoRepository.apagar(produtoEncontrado.getId());
    }
}