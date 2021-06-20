package br.com.eventvs.api.dto.responses;

import lombok.Data;

@Data
public class ParticipanteResponse {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
}
