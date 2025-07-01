package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import jakarta.validation.Valid;

@Service
public class MeioPagamentosService {

    private MeioPagamentoRepository meioPagamentoRepository;

    public MeioPagamentosService(MeioPagamentoRepository meioPagamentoRepository) {
        this.meioPagamentoRepository = meioPagamentoRepository;
    }

    public MeioPagamentoEntity criar(@Valid MeioPagamentoEntity entidade) {
        return meioPagamentoRepository.salvar(entidade);
    }

    public MeioPagamentoEntity buscar(String id) {
        return meioPagamentoRepository.buscar(id, MeioPagamentoEntity.class);
    }

    public List<MeioPagamentoEntity> buscarTodos() {
        return meioPagamentoRepository.buscarTodos(MeioPagamentoEntity.class);
    }

    public MeioPagamentoEntity atualizar(String id, @Valid MeioPagamentoEntity entidade) {
        return meioPagamentoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        meioPagamentoRepository.apagar(id, MeioPagamentoEntity.class);
    }
}
