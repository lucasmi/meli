package br.com.byiorio.desafio.jjson.repository;

import java.util.List;

import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;

public interface IJpaJsonRepository<T extends IJapJsonEntity> {

    <E extends T> E salvar(E entidade);

    <E extends T> E buscar(String id, Class<E> clazz);

    <E extends T> List<E> buscarTodos(Class<E> clazz);

    <E extends T> void apagar(String id, Class<E> clazz);
}
