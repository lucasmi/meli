package br.com.byiorio.desafio.jjson.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcaoEnum {
    REMOVER("remover"),
    INSERIR("inserir"),
    ATUALIZAR("atualizar");

    private final String acao;
}
