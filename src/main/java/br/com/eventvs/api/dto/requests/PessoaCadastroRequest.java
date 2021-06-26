package br.com.eventvs.api.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PessoaCadastroRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}
