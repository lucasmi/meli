package br.com.byiorio.desafio.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDetalhado {
    UsuarioEntity vendedor;
    ProdutoEntity produto;
}
