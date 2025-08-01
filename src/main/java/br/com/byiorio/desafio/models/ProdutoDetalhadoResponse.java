package br.com.byiorio.desafio.models;

import java.util.LinkedList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProdutoDetalhadoResponse {
    VendedorDTO vendedor;
    LinkedList<AvaliacaoDTO> avaliacoes;
    ProdutoDTO produto;
}
