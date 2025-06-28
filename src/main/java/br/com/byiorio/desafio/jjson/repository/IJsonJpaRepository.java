package br.com.byiorio.desafio.jjson.repository;

import br.com.byiorio.desafio.jjson.entity.IJsonJapEntity;

public interface IJsonJpaRepository<T extends IJsonJapEntity> {

    T salvar(T entidade);

    T buscar(String id, Class<T> clazz);
}
