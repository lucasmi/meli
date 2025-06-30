package br.com.byiorio.desafio.jjson.repository;

import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;

public interface IJpaJsonRepository<T extends IJapJsonEntity> {

    T salvar(T entidade);

    <E extends T> E buscar(String id, Class<E> clazz);

    void apagar(String id);
}
