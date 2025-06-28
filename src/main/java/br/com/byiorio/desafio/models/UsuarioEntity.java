package br.com.byiorio.desafio.models;

import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.byiorio.desafio.jjson.entity.IJsonJapEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioEntity implements IJsonJapEntity {
    @JsonProperty(access = Access.READ_ONLY)
    String id;

    @NotBlank
    @Size(max = 100)
    String nome;

    @NotBlank
    @Size(max = 100)
    String email;

    @Builder.Default
    @JsonProperty(access = Access.READ_ONLY)
    ArrayList<MeioPagamentoDTO> formaPagamentos = new ArrayList<MeioPagamentoDTO>();

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String gerarId() {
        return UUID.randomUUID().toString();
    }

}
