package br.com.byiorio.desafio.model;

import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {
    @NotBlank
    @Size(max = 100)
    String nome;

    @NotBlank
    @Size(max = 100)
    String email;

    @Builder.Default
    ArrayList<MeioPagamentoDTO> formaPagamentos = new ArrayList<MeioPagamentoDTO>();
}
