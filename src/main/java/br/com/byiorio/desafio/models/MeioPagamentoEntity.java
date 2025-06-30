package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.annotations.ID;
import br.com.byiorio.desafio.jjson.annotations.OneToOne;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
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
public class MeioPagamentoEntity implements IJapJsonEntity {
    @ID
    @Schema(hidden = true)
    String id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(hidden = true)
    @OneToOne(repository = UsuarioRepository.class, entity = UsuarioEntity.class, mappedBy = "idsMeioPagamentos")
    String idUsuario;

    CartaoCreditoDTO cartaoCredito;

}
