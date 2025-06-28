package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
    String titulo;
    ArrayList<String> imagens;
    BigDecimal preco;
    AvaliacaoDTO avaliacao;
}
