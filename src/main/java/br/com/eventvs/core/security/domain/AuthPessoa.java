package br.com.eventvs.core.security.domain;

import br.com.eventvs.domain.model.Pessoa;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AuthPessoa extends User {

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String role;

    public AuthPessoa(Pessoa pessoa, String role){
        super(pessoa.getEmail(), pessoa.getSenha(), Collections.emptyList());
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.role = role;
    }

}
