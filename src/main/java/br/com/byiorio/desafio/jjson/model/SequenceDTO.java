package br.com.byiorio.desafio.jjson.model;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SequenceDTO {
    // Lista de IDS que estao usando o valor
    HashMap<String, Object> idValor = new HashMap<>();

    // Lista do valor que deve ser unico para o id que esta usando
    HashMap<Object, String> valorId = new HashMap<>();
}
