package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarContaParticipanteController {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private PessoaController pessoaController;



    /**
     * Método responsável pro cadastrar um participante no banco de dados
     *
     * @param pessoaCadastroRequest
     * @return participanteResponse
     * */
    public ParticipanteResponse cadastrarParticipante(PessoaCadastroRequest pessoaCadastroRequest){
        var novaPessoa = pessoaController.cadastrarPessoa(pessoaCadastroRequest);

        Participante novoParticipante = new Participante();
        novoParticipante.setPessoa(novaPessoa);
        participanteRepository.save(novoParticipante);

        return preencherResponse(novaPessoa);
    }

    /**
     * Preenche e retorna um obj de participanteResponse
     *
     * @param novaPessoa
     * @return ParticipanteResponse
     * */
    private ParticipanteResponse preencherResponse(Pessoa novaPessoa) {
        var response = new ParticipanteResponse();
        response.setCpf(novaPessoa.getCpf());
        response.setEmail(novaPessoa.getEmail());
        response.setNome(novaPessoa.getNome());
        response.setId(novaPessoa.getId());
        return response;
    }


}
