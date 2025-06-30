package br.com.byiorio.desafio.models;

import java.util.HashSet;

import br.com.byiorio.desafio.jjson.annotations.ID;
import br.com.byiorio.desafio.jjson.annotations.OneToOne;
import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEntity implements IJapJsonEntity {

    @ID
    @Schema(hidden = true)
    String id;

    @NotBlank
    @Size(max = 100)
    String nome;

    @NotBlank
    @Size(max = 100)
    @Email
    String email;

    @Schema(hidden = true)
    @OneToMany(repository = ProdutoRepository.class, entity = ProdutoEntity.class)
    HashSet<String> idsProdutos = new HashSet<>();

    @Schema(hidden = true)
    @OneToMany(repository = MeioPagamentoRepository.class, entity = MeioPagamentoEntity.class)
    HashSet<String> idsMeioPagamentos = new HashSet<>();

    @Schema(hidden = true)
    @OneToMany(repository = AvaliacaoRepository.class, entity = AvaliacaoEntity.class)
    HashSet<String> idsAvaliacoes = new HashSet<>();

}
