package br.com.byiorio.desafio.jjson.model;

import java.util.HashSet;

import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DestinoDTO {
    String acao;
    HashSet<String> listaIdsTabelaDestino;
    String idPkDestino;
    IJpaJsonRepository<IJapJsonEntity> repositorioDestino;
    IJapJsonEntity entidadeDestino;
}
