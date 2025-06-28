package br.com.byiorio.desafio.models;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
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

    @JsonProperty(access = Access.READ_ONLY)
    HashSet<String> idsProdutos = new HashSet<>();

    @JsonProperty(access = Access.READ_ONLY)
    HashSet<String> idsFormaPagamentos = new HashSet<>();

}
