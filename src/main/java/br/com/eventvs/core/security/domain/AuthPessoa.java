package br.com.eventvs.core.security.domain;

import br.com.eventvs.domain.model.Pessoa;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AuthPessoa extends User {

    private String nome;

    public AuthPessoa(Pessoa pessoa){
        super(pessoa.getEmail(), pessoa.getSenha(), Collections.emptyList());
        this.nome = pessoa.getNome();
    }

}
