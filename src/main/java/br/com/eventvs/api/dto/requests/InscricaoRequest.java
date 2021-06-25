package br.com.eventvs.api.dto.requests;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InscricaoRequest {
	@NotNull
	private Integer participante_id;
	@NotNull
	private Integer evento_id;
}
