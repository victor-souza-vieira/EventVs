package br.com.eventvs.api.dto.responses;

import java.time.LocalDateTime;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Endereco;
import lombok.Data;

@Data
public class EventoInscricaoResponse {
	private Integer id;
	private String nome;
	private LocalDateTime dataHoraInicio;
	private LocalDateTime dataHoraFim;
	private Categoria categoria;
	private StatusEvento statusEvento;
	private Endereco endereco;
}
