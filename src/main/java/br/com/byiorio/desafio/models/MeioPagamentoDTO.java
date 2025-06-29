package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeioPagamentoDTO extends BaseEntity {

    CartaoCreditoDTO cartaoCredito;

}
