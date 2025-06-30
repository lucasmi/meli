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
        // Atualiza data do review
        entidade.setDataReview(LocalDateTime.now()); // Data do review
        return avaliacaoRepository.salvar(entidade);
    }

    public AvaliacaoEntity buscar(String id) {
        return avaliacaoRepository.buscar(id, AvaliacaoEntity.class);
    }

    public List<AvaliacaoEntity> buscarTodos() {
        return avaliacaoRepository.buscarTodos(AvaliacaoEntity.class);
    }

    public AvaliacaoEntity atualizar(String id, @Valid AvaliacaoEntity entidade) {
        // Verifica se existe avaliacao
        AvaliacaoEntity avaliacao = avaliacaoRepository.buscar(id, AvaliacaoEntity.class);

        // Verifica se a relacao de usuario esta correta
        if (!avaliacao.getIdUsuario().equals(entidade.getIdUsuario())) {
            throw new NegocialException("Usuario nao pode ser alterado");
        }

        // Verifica se a relacao de produto esta correta
        if (!avaliacao.getIdProduto().equals(entidade.getIdProduto())) {
            throw new NegocialException("Produto nao pode ser alterado");
        }

        // Verifica se existe usuario
        usuarioRepository.buscar(entidade.getIdUsuario(), UsuarioEntity.class);

        // Verifica se existe produto
        produtoRepository.buscar(entidade.getIdProduto(), ProdutoEntity.class);

        // Salva e retorna valores
        entidade.setDataReview(LocalDateTime.now()); // Atualizada data
        return avaliacaoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        // Apaga avaliacao
        avaliacaoRepository.apagar(id, AvaliacaoEntity.class);
    }
}
