package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.requests.ProdutorCadastroRequest;
import br.com.eventvs.api.dto.responses.ProdutorResponse;
import br.com.eventvs.domain.enums.Situacao;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.model.Administrador;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.ProdutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GerenciarContaProdutor {
    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private GerenciarContaController gerenciarContaController;

    /**
     * Método responsável pro cadastrar um produtor no banco de dados
     *
     * @param pessoaCadastroRequest
     * @return ProdutorResponse - {@link ProdutorResponse}
     * */
    public ProdutorResponse cadastrarProdutor(PessoaCadastroRequest pessoaCadastroRequest){
        var novaPessoa = gerenciarContaController.cadastrarPessoa(pessoaCadastroRequest);

        Produtor novoProdutor = new Produtor();
        novoProdutor.setPessoa(novaPessoa);
        novoProdutor.setSituacao(Situacao.SOLICITADO);
        novoProdutor = produtorRepository.save(novoProdutor);

        return preencherResponse(novaPessoa, novoProdutor);
    }


    /**
     * Método responsável por aceitar o cadastro de um produtor
     *
     * @param produtorId
     * @param emailAdministrador
     * @return {@link ProdutorResponse}
     *
     * */
    public ProdutorResponse aceitarCadastroProdutor(String emailAdministrador, Integer produtorId){
        Pessoa pessoaLogada = gerenciarContaController.login(emailAdministrador);
        gerenciarContaController.loginAdministrador(pessoaLogada);

        Produtor produtor = produtorRepository.findByPessoaId(produtorId).orElseThrow(() -> {
            throw new EntidadeNaoEncontradaException("Não foi encontrado produtor com este código.");
        });

        produtor.setSituacao(Situacao.ACEITO);
        produtorRepository.save(produtor);

        return preencherResponse(produtor.getPessoa(), produtor);
    }

    /**
     * Método responsável por recusar o cadastro de um produtor
     *
     * @param produtorId
     * @param emailAdministrador
     * @return {@link ProdutorResponse}
     *
     * */
    public ProdutorResponse recusarCadastroProdutor(String emailAdministrador, Integer produtorId){
        Pessoa pessoaLogada = gerenciarContaController.login(emailAdministrador);
        gerenciarContaController.loginAdministrador(pessoaLogada);

        Produtor produtor = produtorRepository.findByPessoaId(produtorId).orElseThrow(() -> {
            throw new EntidadeNaoEncontradaException("Não foi encontrado produtor com este código.");
        });

        produtor.setSituacao(Situacao.RECUSADO);
        produtorRepository.save(produtor);

        return preencherResponse(produtor.getPessoa(), produtor);
    }

    /**
     * Método responsável listar todos os produtores que solicitaram conta
     *
     * @param emailAdministrador
     * @return List<{@link ProdutorResponse}>
     *
     * */
    public List<ProdutorResponse> listarSolicitacoesContas(String emailAdministrador){
        Pessoa pessoaLogada = gerenciarContaController.login(emailAdministrador);
        gerenciarContaController.loginAdministrador(pessoaLogada);

        List<Produtor> produtores = produtorRepository.findAllBySituacao(Situacao.SOLICITADO);

        List<ProdutorResponse> produtorResponses = new ArrayList<>();

        produtores.forEach(produtor -> {
            produtorResponses.add(preencherResponse(produtor.getPessoa(), produtor));
        });

        return produtorResponses;
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
