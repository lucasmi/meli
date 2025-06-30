package br.com.byiorio.desafio.services;

import java.time.LocalDateTime;
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
public class AvaliacaoService {
    private UsuarioRepository usuarioRepository;
    private ProdutoRepository produtoRepository;
    private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository,
            AvaliacaoRepository avaliacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public AvaliacaoEntity criar(@Valid AvaliacaoEntity entidade) {
        // Verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Verifica se existe usuario
        ProdutoEntity produtoEncontrado = produtoRepository.buscar(entidade.getIdProduto(),
                ProdutoEntity.class);

        // Salva avaliacao
        entidade.setDataReview(LocalDateTime.now()); // Data do review
        AvaliacaoEntity avaliacao = avaliacaoRepository.salvar(entidade);

        // Salva avaliacao no usuario, para registrar que ele fez essa avaliacao
        usuarioEncontrado.getIdsAvaliacoes().add(avaliacao.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        // Salva avaliacao no produto, para registrar que ele fez essa avaliacao
        produtoEncontrado.getIdsAvaliacoes().add(avaliacao.getId());
        produtoRepository.salvar(produtoEncontrado);

        return avaliacao;
    }

    public AvaliacaoEntity buscar(String id) {
        return avaliacaoRepository.buscar(id, AvaliacaoEntity.class);
    }

    public List<AvaliacaoEntity> buscarTodos() {
        return avaliacaoRepository.buscarTodos(AvaliacaoEntity.class);
    }

    public AvaliacaoEntity atualizar(String id, AvaliacaoEntity entidade) {
        // Verifica se existe usuario
        AvaliacaoEntity avaliacao = avaliacaoRepository.buscar(id, AvaliacaoEntity.class);

        // Verifica se a relacao de usuario esta correta
        if (!avaliacao.getIdUsuario().equals(entidade.getIdUsuario())) {
            throw new NegocialException("Usuario nao pode ser alterado");
        }

        // Verifica se a relacao de usuario esta correta
        if (!avaliacao.getIdProduto().equals(entidade.getIdProduto())) {
            throw new NegocialException("Produto nao pode ser alterado");
        }

        // Verifica se existe usuario
        usuarioRepository.buscar(entidade.getIdUsuario(), UsuarioEntity.class);

        // Verifica se existe usuario
        produtoRepository.buscar(entidade.getIdProduto(), ProdutoEntity.class);

        // Salva e retorna valores
        entidade.setDataReview(LocalDateTime.now()); // Atualizada data
        return avaliacaoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        // Encontra avaliacao
        AvaliacaoEntity avaliacaoEncontrada = avaliacaoRepository.buscar(id,
                AvaliacaoEntity.class);

        // Verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(avaliacaoEncontrada.getIdUsuario(),
                UsuarioEntity.class);

        // Verifica se existe usuario
        ProdutoEntity produtoEncontrado = produtoRepository.buscar(avaliacaoEncontrada.getIdProduto(),
                ProdutoEntity.class);

        // Apaga os vinculos das avaliacoes com o usuario e produto
        // Remove vinculo do usuario com a avaliacao
        usuarioEncontrado.getIdsAvaliacoes().remove(id);
        usuarioRepository.salvar(usuarioEncontrado);

        // Remove vinculo do produto com a avaliacao
        produtoEncontrado.getIdsAvaliacoes().remove(id);
        produtoRepository.salvar(produtoEncontrado);

        // Apaga avaliacao
        avaliacaoRepository.apagar(id);
    }
}
