package br.com.byiorio.desafio.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosDTO {
    ArrayList<ProdutoDetalhado> produtos;
}
