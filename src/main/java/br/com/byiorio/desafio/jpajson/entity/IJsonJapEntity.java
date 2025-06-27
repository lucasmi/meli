package br.com.byiorio.desafio.jpajson.entity;

public interface IJsonJapEntity<T> {

    public String getIdentificador();

    public T criarEntidade();
}
