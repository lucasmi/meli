package br.com.byiorio.desafio.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.model.ProdutoDetalhado;
import br.com.byiorio.desafio.model.ProdutosDTO;

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
