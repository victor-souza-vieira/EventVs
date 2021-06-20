package br.com.eventvs.api.dto.requests;

import java.time.LocalDateTime;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Endereco;
import lombok.Data;

@Data
public class EventoRequest {
	
    private Integer id;

    private String nome;

    private String descricao;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    private Integer categoriaId;

    private StatusEvento statusEvento;

    private Endereco endereco;
}
