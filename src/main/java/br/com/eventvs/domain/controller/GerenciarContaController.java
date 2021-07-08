package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Administrador;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.AdministradorRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenciarContaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    /**
     * Método responsável por verificar se uma pessoa realmente existe no banco de dados
     * para autorizar o acesso aos endpoints que o utilizem.
     *
     * @param email
     * @return Pessoa
     * @throws NegocioException {@link NegocioException}
     * */
    public Pessoa login(String email){
        return pessoaRepository.findByEmail(email).orElseThrow(() -> {
            throw new NegocioException("Usuário não está logado no sistema.");
        });
    }

    /**
     * Método responsável por verificar se uma pessoa logada na api é um produtor
     *
     * @param pessoa
     * @return Produtor
     * @throws NegocioException {@link NegocioException}
     * */
    public Produtor loginProdutor(Pessoa pessoa){
        return produtorRepository.findByPessoaId(pessoa.getId()).orElseThrow(() -> {
            throw new NegocioException("Usuário não é um produtor de eventos.");
        });
    }

    /**
     * Método responsável por verificar se uma pessoa logada na api é um produtor
     *
     * @param pessoa
     * @return Produtor
     * @throws NegocioException {@link NegocioException}
     * */
    public Participante loginParticipante(Pessoa pessoa){
        return participanteRepository.findByPessoaEmail(pessoa.getEmail()).orElseThrow(() -> {
            throw new NegocioException("O usuário não é um participante.");
        });
    }

    /**
     * Método responsável por verificar se uma pessoa logada na api é um administrador
     *
     * @param pessoa
     * @return Administrador
     * @throws NegocioException {@link NegocioException}
     * */
    public Administrador loginAdministrador(Pessoa pessoa){
        return administradorRepository.findByPessoaId(pessoa.getId()).orElseThrow(() -> {
            throw new NegocioException("Usuário não é um administrador.");
        });
    }



}
