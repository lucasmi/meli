package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeioPagamentoEntity extends BaseEntity {
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(hidden = true)
    String idUsuario;

    // @JsonInclude(JsonInclude.Include.NON_NULL)
    CartaoCreditoDTO cartaoCredito;

}
