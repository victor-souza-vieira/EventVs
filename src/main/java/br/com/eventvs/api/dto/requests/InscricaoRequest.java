package br.com.eventvs.api.dto.requests;

import br.com.eventvs.domain.model.Participante;
import lombok.Data;

@Data
public class InscricaoRequest {
	
	private Integer participante_id;
	private Integer evento_id;
}
