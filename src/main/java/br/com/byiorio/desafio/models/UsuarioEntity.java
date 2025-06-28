package br.com.byiorio.desafio.models;

import java.util.HashSet;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEntity extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    String nome;

    @NotBlank
    @Size(max = 100)
    String email;

    @Schema(hidden = true)
    HashSet<String> idsProdutos = new HashSet<>();

    @Schema(hidden = true)
    HashSet<String> idsMeioPagamentos = new HashSet<>();

    @Schema(hidden = true)
    HashSet<String> idsAvaliacoes = new HashSet<>();

}
