package br.com.byiorio.desafio.models;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PixDTO {
    @Size(max = 1000)
    String chavePix;
}
