package br.com.byiorio.desafio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModoPagamentoEnum {
    CARTAO_CREDITO(1),
    PAY_PAL(2),
    CARTAO_DEBITO(3);

    private int tipo;

}
