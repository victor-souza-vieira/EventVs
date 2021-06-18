package br.com.eventvs.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class Problema {

	@Getter @Setter private Integer status;
	@Getter @Setter private OffsetDateTime dataHora;
	@Getter @Setter private String message;
	@Getter @Setter private List<Campo> campos;
	
	@AllArgsConstructor
	public static class Campo {
		@Getter @Setter private String nome;
		@Getter @Setter private String mensagem;
	}
	
	
}
