package br.com.eventvs.api.dto.requests;

import lombok.Data;

@Data
public class ParticipanteCadastroRequest {

    private String nome;
    private String cpf;
    private String email;
    private String senha;
}
