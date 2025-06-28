package br.com.byiorio.desafio.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {
	private String codigo;
	private String msg;
}
