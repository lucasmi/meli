package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.byiorio.desafio.jjson.entity.IJsonJapEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoEntity implements IJsonJapEntity {
    @JsonProperty(access = Access.READ_ONLY)
    String id;

    @NotBlank
    @Size(min = 36, max = 36)
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

    ArrayList<String> avaliacoesIds;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String gerarId() {
        return UUID.randomUUID().toString();
    }
}
