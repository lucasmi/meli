package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.annotations.ManyToMany;
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
    @ManyToOne(repository = UsuarioRepository.class, entity = UsuarioEntity.class, mappedBy = "idsProdutos", blockOnUpdateOf = ProdutoRepository.class)
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
    @OneToMany(repository = AvaliacaoRepository.class, entity = AvaliacaoEntity.class)
    HashSet<String> idsAvaliacoes = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(hidden = true)
    @ManyToMany(repository = CategoriaRepository.class, entity = CategoriaEntity.class, mappedBy = "id")
    String idCategoria;

}
