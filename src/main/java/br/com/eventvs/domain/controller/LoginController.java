package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.PessoaRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProdutorRepository produtorRepository;

    public Pessoa login(String email){
        return pessoaRepository.findByEmail(email).orElseThrow(() -> {
            throw new NegocioException("Usuário não está logado no sistema.");
        });
    }

    public Produtor login(Pessoa pessoa){
        return produtorRepository.findByPessoaId(pessoa.getId()).orElseThrow(() -> {
            throw new NegocioException("Usuário não é um produtor de eventos.");
        });
    }

}
