package br.com.byiorio.desafio.jjson.repository;

import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;

public interface IJpaJsonRepository<T extends IJapJsonEntity> {

    T salvar(T entidade);

    T buscar(String id, Class<T> clazz);

    void apagar(String id);
}
