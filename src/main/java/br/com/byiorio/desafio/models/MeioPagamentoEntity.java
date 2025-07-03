package br.com.byiorio.desafio.models;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
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
    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GeneratedValue.Strategy.UUID)
    String id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(hidden = true)
    @ManyToOne(repositoryTarget = UsuarioRepository.class, entityTarget = UsuarioEntity.class, mappedBy = "idsMeioPagamentos", blockOnUpdate = true, repositorySource = MeioPagamentoRepository.class, entitySource = MeioPagamentoEntity.class)
    String idUsuario;

    CartaoCreditoDTO cartaoCredito;

}
