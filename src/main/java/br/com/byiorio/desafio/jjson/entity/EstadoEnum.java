package br.com.byiorio.desafio.jjson.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoEnum {
    ATUALIZAR("ATUALIZAR"),
    REMOVER("REMOVER");

    private final String estado;

}
