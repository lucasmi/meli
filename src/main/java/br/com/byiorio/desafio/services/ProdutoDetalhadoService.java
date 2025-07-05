package br.com.byiorio.desafio.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.AvaliacaoDTO;
import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.models.CategoriaDTO;
import br.com.byiorio.desafio.models.CategoriaEntity;
import br.com.byiorio.desafio.models.ComentaristaDTO;
import br.com.byiorio.desafio.models.ProdutoDTO;
import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.models.VendedorDTO;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.CategoriaRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;

@Service
public class ProdutoDetalhadoService {

        private ProdutoRepository produtoRepository;
        private UsuarioRepository usuarioRepository;
        private AvaliacaoRepository avaliacaoRepository;
        private CategoriaRepository categoriaRepository;

        public ProdutoDetalhadoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository,
                        AvaliacaoRepository avaliacaoRepository, CategoriaRepository categoriaRepository) {
                this.produtoRepository = produtoRepository;
                this.usuarioRepository = usuarioRepository;
                this.avaliacaoRepository = avaliacaoRepository;
                this.categoriaRepository = categoriaRepository;
        }

        public ProdutoDetalhadoResponse buscar(String id) {
                // Carrega produto
                ProdutoEntity produtoEntity = produtoRepository.buscar(id, ProdutoEntity.class);

                // Carrega vendendor
                UsuarioEntity usuarioEntity = usuarioRepository.buscar(produtoEntity.getIdUsuario(),
                                UsuarioEntity.class);

                // Carrega categoria
                CategoriaEntity categoriaEntity = categoriaRepository.buscar(produtoEntity.getIdCategoria(),
                                CategoriaEntity.class);

                // Carrega avaliacoes e seus detalhes
                LinkedList<AvaliacaoDTO> avaliacoes = new LinkedList<>();
                produtoEntity.getIdsAvaliacoes().forEach(idAvaliacao -> {
                        // Carrega avaliacoes
                        AvaliacaoEntity avaliacaoEntity = avaliacaoRepository.buscar(idAvaliacao,
                                        AvaliacaoEntity.class);

                        // Carrega usuario que avaliadou
                        UsuarioEntity comentaristaEntity = usuarioRepository.buscar(avaliacaoEntity.getIdUsuario(),
                                        UsuarioEntity.class);

                        ComentaristaDTO comentarista = ComentaristaDTO.builder()
                                        .id(comentaristaEntity.getId())
                                        .email(comentaristaEntity.getEmail())
                                        .nome(comentaristaEntity.getNome())
                                        .build();

                        // Insere
                        avaliacoes.add(AvaliacaoDTO.builder()
                                        .id(avaliacaoEntity.getId())
                                        .comentario(avaliacaoEntity.getComentario())
                                        .dataReview(avaliacaoEntity.getDataReview())
                                        .nota(avaliacaoEntity.getNota())
                                        .usuario(comentarista)
                                        .build());

                });

                // Monta produto detalhado
                VendedorDTO vendedor = VendedorDTO.builder()
                                .nome(usuarioEntity.getNome())
                                .email(usuarioEntity.getEmail())
                                .id(usuarioEntity.getId())
                                .build();

                // Monta categoria
                CategoriaDTO categoria = CategoriaDTO.builder()
                                .nome(categoriaEntity.getNome())
                                .id(categoriaEntity.getId())
                                .build();

                // Monta produto
                ProdutoDTO produto = ProdutoDTO.builder()
                                .titulo(produtoEntity.getTitulo())
                                .descricao(produtoEntity.getDescricao())
                                .preco(produtoEntity.getPreco())
                                .imagens(produtoEntity.getImagens())
                                .id(produtoEntity.getId())
                                .categoria(categoria)
                                .build();

                return ProdutoDetalhadoResponse.builder()
                                .produto(produto)
                                .avaliacoes(avaliacoes)
                                .vendedor(vendedor)
                                .build();
        }

        public List<ProdutoDetalhadoResponse> buscarTodos(String idCategoria) {
                // Para cada produto carregar todos os detalhes
                LinkedList<ProdutoDetalhadoResponse> produtosDetalhado = new LinkedList<>();

                // Pega todos os arquivos
                LinkedList<ProdutoEntity> todosProdutos = null;

                // Se nao tiver filtro pega tudo, se nao filtra por id de Categoria
                if (idCategoria == null) {
                        todosProdutos = new LinkedList<>(
                                        produtoRepository.buscarTodos(ProdutoEntity.class));
                } else {
                        todosProdutos = new LinkedList<>(
                                        produtoRepository.buscarPorCampo(ProdutoEntity.class, "idCategoria",
                                                        idCategoria));
                }

                // Coloca detalhes de cada produto
                todosProdutos.forEach(produto -> produtosDetalhado.add(this.buscar(produto.getId())));

                return produtosDetalhado;
        }

}