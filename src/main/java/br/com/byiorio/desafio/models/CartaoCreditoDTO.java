package br.com.byiorio.desafio.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoCreditoDTO {
    @NotBlank
    @Size(max = 30)
    String numero;

    @NotBlank
    @Size(max = 19)
    String nomeEmbossing;

    @Schema(pattern = "MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    String dataVencimentoCartao;
}
