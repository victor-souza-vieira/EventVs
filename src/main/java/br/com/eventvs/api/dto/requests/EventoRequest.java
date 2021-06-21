package br.com.eventvs.api.dto.requests;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Endereco;
import lombok.Data;

@Data
public class EventoRequest {

    private Integer id;
	
	@NotBlank
    private String nome;
	
	@NotBlank
    private String descricao;

	@NotNull
    private LocalDateTime dataHoraInicio;
	
	@NotNull
    private LocalDateTime dataHoraFim;
	
	@NotNull
    private Integer categoriaId;

	@NotNull
    private StatusEvento statusEvento;

	@NotNull
    private Endereco endereco;
}
