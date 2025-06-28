package br.com.byiorio.desafio.jjson.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JsonJpaException extends RuntimeException {
    private String descricao;

}
