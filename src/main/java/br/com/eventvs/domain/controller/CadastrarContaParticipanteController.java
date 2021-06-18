package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.ParticipanteCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CadastrarContaParticipanteController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método responsável pro cadastrar um participante no banco de dados
     *
     * @param participanteCadastroRequest
     * @return participanteResponse
     * */
    public ParticipanteResponse cadastrarParticipante(ParticipanteCadastroRequest participanteCadastroRequest){
        Optional<Pessoa> pessoaJaCadastrada = pessoaRepository.findByCpf(participanteCadastroRequest.getCpf());
        if(pessoaJaCadastrada.isPresent()){
            log.warn("Já existe uma pessoa cadastrado com este CPF: " + participanteCadastroRequest.getCpf());
            return null;
        }

        pessoaJaCadastrada = pessoaRepository.findByEmail(participanteCadastroRequest.getEmail());
        if(pessoaJaCadastrada.isPresent()){
            log.warn("Já existe uma pessoa cadastrado com este EMAIL: " + participanteCadastroRequest.getEmail());
            return null;
        }

        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(participanteCadastroRequest.getCpf());
        novaPessoa.setEmail(participanteCadastroRequest.getEmail());
        novaPessoa.setNome(participanteCadastroRequest.getNome());
        novaPessoa.setSenha(passwordEncoder.encode(participanteCadastroRequest.getSenha()));

        novaPessoa = pessoaRepository.save(novaPessoa);

        Participante novoParticipante = new Participante();
        novoParticipante.setPessoa(novaPessoa);

        novoParticipante = participanteRepository.save(novoParticipante);

        ParticipanteResponse response = new ParticipanteResponse();
        response.setCpf(novaPessoa.getCpf());
        response.setEmail(novaPessoa.getEmail());
        response.setNome(novaPessoa.getNome());
        response.setId(novaPessoa.getId());

        return response;

    }



}
