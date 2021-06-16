package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.requests.ParticipanteCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.domain.controller.CadastrarContaParticipanteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.eventvs.api.util.Paths.PATH_CRIAR_CONTA;
import static br.com.eventvs.api.util.Paths.PATH_CRIAR_CONTA_PARTICIPANTE;

@RestController
@RequestMapping(value = PATH_CRIAR_CONTA)
public class CriarContasResource {

    @Autowired
    private CadastrarContaParticipanteController cadastrarContaParticipanteController;

    /**
     * Responsável por receber e repassar a requisição para o CadastrarContaParticipanteController <br />
     * para que seja cadastrada a conta de um novo usuário
     *
     * @param participanteCadastroRequest
     * @return
     * */
    @PostMapping(PATH_CRIAR_CONTA_PARTICIPANTE)
    public ResponseEntity<ParticipanteResponse> cadastrarParticipante(@RequestBody ParticipanteCadastroRequest participanteCadastroRequest){
        ParticipanteResponse response = cadastrarContaParticipanteController.cadastrarParticipante(participanteCadastroRequest);
        if(response != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().build();
    }

}
