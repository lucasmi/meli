package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoEntity extends BaseEntity {
    @NotBlank
    @Size(min = 1, max = 50)
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

    @JsonProperty(access = Access.READ_ONLY)
    HashSet<String> idAvaliacoes;

}
