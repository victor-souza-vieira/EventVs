package br.com.eventvs.api.dto.responses;

import java.time.LocalDateTime;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Endereco;
import br.com.eventvs.domain.model.Participante;
import lombok.Data;

@Data
public class InscricaoResponse {
	private Integer id;
	private LocalDateTime dataHora;
	private Boolean isCancelada;

	private ParticipanteInscricaoResponse participante;

	private EventoInscricaoResponse evento;
	
}

