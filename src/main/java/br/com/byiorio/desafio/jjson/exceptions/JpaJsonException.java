package br.com.byiorio.desafio.jjson.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JpaJsonException extends RuntimeException {
    final String descricao;
}
