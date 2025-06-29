package br.com.byiorio.desafio.models;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BasicErrorDTO {
	private ArrayList<ErrorDTO> errors;
}
