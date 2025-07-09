package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.CategoriaRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoEntity implements IJapJsonEntity {
    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GeneratedValue.Strategy.UUID)
    String id;

    @NotBlank
    @Size(min = 1, max = 50)
    @ManyToOne(repositoryTarget = UsuarioRepository.class, entityTarget = UsuarioEntity.class, mappedBy = "idsProdutos", blockOnUpdate = true, repositorySource = ProdutoRepository.class, entitySource = ProdutoEntity.class)
    String idUsuario;

    @NotBlank
    @Size(max = 200)
    String titulo;

    @NotBlank
    @Size(max = 500)
    String descricao;

    @Valid
    ArrayList<ImagemDTO> imagens;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Digits(integer = 20, fraction = 2)
    BigDecimal preco;

    @Schema(hidden = true)
    @OneToMany(repositoryTarget = AvaliacaoRepository.class, entityTarget = AvaliacaoEntity.class, repositorySource = ProdutoRepository.class, entitySource = ProdutoEntity.class)
    HashSet<String> idsAvaliacoes = new HashSet<>();

    @Size(min = 1, max = 50)
    @NotBlank
    @ManyToOne(repositoryTarget = CategoriaRepository.class, entityTarget = CategoriaEntity.class, mappedBy = "idsProdutos", blockOnUpdate = true, repositorySource = ProdutoRepository.class, entitySource = ProdutoEntity.class)
    String idCategoria;

}
