package br.com.byiorio.desafio.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.ProdutoDetalhado;
import br.com.byiorio.desafio.models.ProdutosDTO;

@Service
public class ProdutoService {

    public ProdutosDTO consultar() {
        ProdutosDTO produtos = new ProdutosDTO();

        ArrayList<ProdutoDetalhado> produtosDetalhado = new ArrayList<ProdutoDetalhado>();

        ProdutoDetalhado pd = new ProdutoDetalhado();

        pd.setVendedor(null);

        produtos.setProdutos(produtosDetalhado);
        return produtos;
    }
}
