package br.com.byiorio.desafio.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.byiorio.desafio.jjson.annotations.ID;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
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
    @ID
    @Schema(hidden = true)
    String id;

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

    @Schema(hidden = true)
    HashSet<String> idsAvaliacoes = new HashSet<>();

}
