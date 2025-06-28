package br.com.byiorio.desafio.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.AvaliacaoDTO;
import br.com.byiorio.desafio.models.AvaliacaoDetalhe;
import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.models.ComentaristaDTO;
import br.com.byiorio.desafio.models.ProdutoDTO;
import br.com.byiorio.desafio.models.ProdutoDetalhadoDTO;
import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.models.VendedorDTO;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;

@Service
public class ProdutoDetalhadoService {

    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private AvaliacaoRepository avaliacaoRepository;

    public ProdutoDetalhadoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository,
            AvaliacaoRepository avaliacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public ProdutoDetalhadoDTO buscar(String id) {
        // Carrega produto
        ProdutoEntity produtoEntity = (ProdutoEntity) produtoRepository.buscar(id, ProdutoEntity.class);

        // Carrega vendendor
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioRepository.buscar(produtoEntity.getIdUsuario(),
                UsuarioEntity.class);

        // Carrega avaliacoes e seus detalhes
        LinkedList<AvaliacaoDTO> avaliacoes = new LinkedList<>();
        produtoEntity.getIdsAvaliacoes().forEach(idAvaliacao -> {
            AvaliacaoDTO avaliacao = new AvaliacaoDTO();

            // Carrega avaliacoes
            AvaliacaoEntity avaliacaoEntity = (AvaliacaoEntity) avaliacaoRepository.buscar(idAvaliacao,
                    AvaliacaoEntity.class);
            avaliacao.setAvaliacao(AvaliacaoDetalhe.builder()
                    .comentario(avaliacaoEntity.getComentario())
                    .dataReview(avaliacaoEntity.getDataReview())
                    .nota(avaliacaoEntity.getNota())
                    .build());

            // Carrega usuario que avaliadou
            UsuarioEntity comentarista = (UsuarioEntity) usuarioRepository.buscar(avaliacaoEntity.getIdUsuario(),
                    UsuarioEntity.class);

            avaliacao.setUsuario(ComentaristaDTO.builder()
                    .id(comentarista.getId())
                    .email(comentarista.getEmail())
                    .nome(comentarista.getNome())
                    .build());

            // Insere
            avaliacoes.add(avaliacao);

        });

        // Monta produto detalhado
        VendedorDTO vendedor = VendedorDTO.builder()
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .id(usuarioEntity.getId())
                .build();

        // Monta produto
        ProdutoDTO produto = ProdutoDTO.builder()
                .titulo(produtoEntity.getTitulo())
                .descricao(produtoEntity.getDescricao())
                .preco(produtoEntity.getPreco())
                .imagens(produtoEntity.getImagens())
                .id(produtoEntity.getId())
                .build();

        return ProdutoDetalhadoDTO.builder()
                .produto(produto)
                .avaliacoes(avaliacoes)
                .vendedor(vendedor)
                .build();
    }

    public List<ProdutoDetalhadoDTO> buscarTodos() {
        // Para cada produto carregar todos os detalhes
        LinkedList<ProdutoDetalhadoDTO> produtosDetalhado = new LinkedList<>();

        // Pega todos os arquivos
        LinkedList<String> todosArquivos = new LinkedList<>(produtoRepository.buscarTodos());

        // adiciona usuarios
        todosArquivos.forEach(idProduto -> produtosDetalhado.add(this.buscar(idProduto)));

        return produtosDetalhado;

    }

}