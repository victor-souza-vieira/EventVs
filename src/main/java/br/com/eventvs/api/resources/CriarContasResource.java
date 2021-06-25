package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.api.dto.responses.ProdutorResponse;
import br.com.eventvs.domain.controller.CadastrarContaParticipanteController;
import br.com.eventvs.domain.controller.CadastrarContaProdutorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.eventvs.api.util.Paths.*;

@RestController
@RequestMapping(value = PATH_CRIAR_CONTA)
public class CriarContasResource {

    @Autowired
    private CadastrarContaParticipanteController cadastrarContaParticipanteController;

    @Autowired
    private CadastrarContaProdutorController cadastrarContaProdutorController;

    /**
     * Responsável por receber e repassar a requisição para o CadastrarContaParticipanteController <br />
     * para que seja cadastrada a conta de um novo usuário
     *
     * @param pessoaCadastroRequest
     * @return
     * */
    @PostMapping(PATH_CRIAR_CONTA_PARTICIPANTE)
    public ResponseEntity<ParticipanteResponse> cadastrarParticipante(@RequestBody PessoaCadastroRequest pessoaCadastroRequest){
        ParticipanteResponse response = cadastrarContaParticipanteController.cadastrarParticipante(pessoaCadastroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Responsável por receber e repassar a requisição para o CadastrarContaProdutorController <br />
     * para que seja cadastrada a conta de um novo usuário
     *
     * @param pessoaCadastroRequest
     * @return
     * */
    @PostMapping(PATH_CRIAR_CONTA_PRODUTOR)
    public ResponseEntity<ProdutorResponse> cadastrarProdutor(@RequestBody PessoaCadastroRequest pessoaCadastroRequest){
        ProdutorResponse response = cadastrarContaProdutorController.cadastrarProdutor(pessoaCadastroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
