package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProdutoDTO {
    String id;
    String titulo;
    String descricao;
    ArrayList<ImagemDTO> imagens;
    BigDecimal preco;
    CategoriaDTO categoria;
}
