package br.com.byiorio.desafio.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
