package br.com.byiorio.desafio.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NegocialException extends RuntimeException {
    private String descricao;

}
