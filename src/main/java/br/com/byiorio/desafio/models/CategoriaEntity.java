package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaEntity implements IJapJsonEntity {

    @Id
    @GeneratedValue(strategy = GeneratedValue.Strategy.UUID)
    @Schema(hidden = true)
    String id;

    @NotBlank
    @Size(max = 100)
    String nome;

}
