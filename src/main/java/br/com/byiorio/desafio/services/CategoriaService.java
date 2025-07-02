package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.CategoriaEntity;
import br.com.byiorio.desafio.repositories.CategoriaRepository;
import jakarta.validation.Valid;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaEntity criar(@Valid CategoriaEntity entidade) {
        return categoriaRepository.salvar(entidade);
    }

    public CategoriaEntity buscar(String id) {
        return categoriaRepository.buscar(id, CategoriaEntity.class);
    }

    public List<CategoriaEntity> buscarTodos() {
        return categoriaRepository.buscarTodos(CategoriaEntity.class);
    }

    public CategoriaEntity atualizar(String id, @Valid CategoriaEntity entidade) {
        return categoriaRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        categoriaRepository.apagar(id, CategoriaEntity.class);
    }
}
