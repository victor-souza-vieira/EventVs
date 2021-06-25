package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.responses.ProdutorResponse;
import br.com.eventvs.domain.enums.Situacao;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.ProdutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GerenciarContaProdutor {

    @Autowired
    private PessoaController pessoaController;

    @Autowired
    private ProdutorRepository produtorRepository;

    /**
     * Método responsável pro cadastrar um produtor no banco de dados
     *
     * @param pessoaCadastroRequest
     * @return ProdutorResponse - {@link ProdutorResponse}
     * */
    public ProdutorResponse cadastrarProdutor(PessoaCadastroRequest pessoaCadastroRequest){
        var novaPessoa = pessoaController.cadastrarPessoa(pessoaCadastroRequest);

        Produtor novoProdutor = new Produtor();
        novoProdutor.setPessoa(novaPessoa);
        novoProdutor.setSituacao(Situacao.SOLICITADO);
        novoProdutor = produtorRepository.save(novoProdutor);

        return preencherResponse(novaPessoa, novoProdutor);
    }
    /**
     * Preenche e retorna um obj de produtorResponse
     *
     * @param novaPessoa
     * @return ProdutorResponse
     * */
    private ProdutorResponse preencherResponse(Pessoa novaPessoa, Produtor novoProdutor) {
        ProdutorResponse response = new ProdutorResponse();
        response.setCpf(novaPessoa.getCpf());
        response.setEmail(novaPessoa.getEmail());
        response.setNome(novaPessoa.getNome());
        response.setId(novaPessoa.getId());
        response.setSituacao(novoProdutor.getSituacao().name());
        return response;
    }


}
