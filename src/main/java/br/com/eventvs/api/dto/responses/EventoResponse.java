package br.com.eventvs.api.dto.responses;

import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Endereco;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoResponse {

    private Integer id;
    private String nome;
    private String descricao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Categoria categoria;
    private String statusEvento;
    private String produtor;
    private Endereco endereco;

}
