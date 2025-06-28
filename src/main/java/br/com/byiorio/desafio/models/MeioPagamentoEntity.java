package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeioPagamentoEntity extends BaseEntity {
    @NotBlank
    @Size(min = 1, max = 50)
    String idUsuario;

    // @JsonInclude(JsonInclude.Include.NON_NULL)
    CartaoCreditoDTO cartaoCredito;

    // @JsonInclude(JsonInclude.Include.NON_NULL)
    // private PixDTO pix;
}
