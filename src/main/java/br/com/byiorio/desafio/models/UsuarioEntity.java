package br.com.byiorio.desafio.models;

import java.util.HashSet;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEntity implements IJapJsonEntity {

    @Id
    @GeneratedValue(strategy = GeneratedValue.Strategy.UUID)
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
    @OneToMany(repositoryTarget = ProdutoRepository.class, entityTarget = ProdutoEntity.class, repositorySource = UsuarioRepository.class, entitySource = UsuarioEntity.class)
    HashSet<String> idsProdutos = new HashSet<>();

    @Schema(hidden = true)
    @OneToMany(repositoryTarget = MeioPagamentoRepository.class, entityTarget = MeioPagamentoEntity.class, repositorySource = UsuarioRepository.class, entitySource = UsuarioEntity.class)
    HashSet<String> idsMeioPagamentos = new HashSet<>();

    @Schema(hidden = true)
    @OneToMany(repositoryTarget = AvaliacaoRepository.class, entityTarget = AvaliacaoEntity.class, repositorySource = UsuarioRepository.class, entitySource = UsuarioEntity.class)
    HashSet<String> idsAvaliacoes = new HashSet<>();

}
