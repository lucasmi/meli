package br.com.byiorio.desafio.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import jakarta.validation.Valid;

@Service
public class AvaliacaoService {
    private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
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
        // Salva e retorna valores
        entidade.setDataReview(LocalDateTime.now()); // Atualizada data
        return avaliacaoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        // Apaga avaliacao
        avaliacaoRepository.apagar(id, AvaliacaoEntity.class);
    }
}
