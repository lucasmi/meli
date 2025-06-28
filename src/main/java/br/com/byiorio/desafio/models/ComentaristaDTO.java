package br.com.byiorio.desafio.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ComentaristaDTO {
    String id;
    String nome;
    String email;
}
