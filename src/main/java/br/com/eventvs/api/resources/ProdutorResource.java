package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.responses.ProdutorResponse;
import br.com.eventvs.core.security.EventvsSecurity;
import br.com.eventvs.domain.controller.GerenciarContaProdutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.eventvs.api.util.Paths.*;

@RestController
@RequestMapping(value = PATH_PRODUTOR)
public class ProdutorResource {

    @Autowired
    private GerenciarContaProdutor gerenciarContaProdutor;

    @Autowired
    private EventvsSecurity eventvsSecurity;

    /**
     * Aceitar conta de produtor
     *
     * @param produtorId
     * @return
     * */
    @GetMapping(value = PATH_PRODUTOR_ACEITAR_CADASTRO)
    public ProdutorResponse aceitarCadastroProdutor(@PathVariable Integer produtorId){
        String email = eventvsSecurity.getPessoaEmail();
        return gerenciarContaProdutor.aceitarCadastroProdutor(email, produtorId);
    }


    /**
     * Recusar conta de produtor
     *
     * @param produtorId
     * @return
     * */
    @GetMapping(value = PATH_PRODUTOR_RECUSAR_CADASTRO)
    public ProdutorResponse recusarCadastroProdutor(@PathVariable Integer produtorId){
        String email = eventvsSecurity.getPessoaEmail();
        return gerenciarContaProdutor.recusarCadastroProdutor(email, produtorId);
    }

    /**
     * Listar solicitações de contas de produtor
     *
     * @param
     * @return
     * */
    @GetMapping(value = PATH_PRODUTOR_LISTAR_SOLICITADOS)
    public List<ProdutorResponse> listarContasSolicitadas(){
        String email = eventvsSecurity.getPessoaEmail();
        return gerenciarContaProdutor.listarSolicitacoesContas(email);
    }

}
