package br.com.eventvs.api.dto.responses;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class InscricaoResponse {
	private Integer id;
	private LocalDateTime dataHora;
	private Boolean isCancelada;

	private ParticipanteInscricaoResponse participante;

	private EventoInscricaoResponse evento;
}

