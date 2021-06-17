package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.ParticipanteCadastroRequest;
import br.com.eventvs.api.dto.requests.ProdutorCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.api.dto.responses.ProdutorResponse;
import br.com.eventvs.domain.enums.Situacao;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CadastrarContaProdutorController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método responsável pro cadastrar um produtor no banco de dados
     *
     * @param produtorCadastroRequest
     * @return participanteResponse
     * */
    public ProdutorResponse cadastrarProdutor(ProdutorCadastroRequest produtorCadastroRequest){
        Optional<Pessoa> pessoaJaCadastrada = pessoaRepository.findByCpf(produtorCadastroRequest.getCpf());
        if(pessoaJaCadastrada.isPresent()){
            log.warn("Já existe uma pessoa cadastrado com este CPF: " + produtorCadastroRequest.getCpf());
            return null;
        }

        pessoaJaCadastrada = pessoaRepository.findByEmail(produtorCadastroRequest.getEmail());
        if(pessoaJaCadastrada.isPresent()){
            log.warn("Já existe uma pessoa cadastrado com este EMAIL: " + produtorCadastroRequest.getEmail());
            return null;
        }

        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCpf(produtorCadastroRequest.getCpf());
        novaPessoa.setEmail(produtorCadastroRequest.getEmail());
        novaPessoa.setNome(produtorCadastroRequest.getNome());
        novaPessoa.setSenha(passwordEncoder.encode(produtorCadastroRequest.getSenha()));

        novaPessoa = pessoaRepository.save(novaPessoa);

        Produtor novoProdutor = new Produtor();
        novoProdutor.setPessoa(novaPessoa);
        novoProdutor.setSituacao(Situacao.SOLICITADO);

        novoProdutor = produtorRepository.save(novoProdutor);

        ProdutorResponse response = new ProdutorResponse();
        response.setCpf(novaPessoa.getCpf());
        response.setEmail(novaPessoa.getEmail());
        response.setNome(novaPessoa.getNome());
        response.setId(novaPessoa.getId());
        response.setSituacao(novoProdutor.getSituacao().name());

        return response;
    }



}
