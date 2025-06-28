package br.com.byiorio.desafio.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BasicErrorDTO {
	private Object data;
	private Object errors;
}
