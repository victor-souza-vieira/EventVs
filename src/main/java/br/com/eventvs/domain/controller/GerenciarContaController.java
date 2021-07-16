package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.responses.PessoaResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Método responsável por cadastrar uma pessoa.
     *
     * @param pessoaRequest
     * @return Pessoa
     *
     * */
    public Pessoa cadastrarPessoa(PessoaCadastroRequest pessoaRequest){
        checarCPFExistenteThrows(pessoaRequest);

        checarEmailExistenteThrows(pessoaRequest);

        var novaPessoa = new Pessoa();
        novaPessoa.setCpf(pessoaRequest.getCpf());
        novaPessoa.setEmail(pessoaRequest.getEmail());
        novaPessoa.setNome(pessoaRequest.getNome());
        novaPessoa.setSenha(passwordEncoder.encode(pessoaRequest.getSenha()));

        return pessoaRepository.save(novaPessoa);
    }
    
    /**
     * Método responsável por alterar os dados uma pessoa.
     *
     * @param pessoaRequest
     * @param email
     * @return Pessoa
     *
     * */
    public PessoaResponse alterarDados(PessoaCadastroRequest pessoaRequest, String email) {
    	Pessoa pessoa = login(email);
    	if(pessoaRequest.getSenha() != null) {
    		pessoa.setSenha(passwordEncoder.encode(pessoaRequest.getSenha()));
    	}
    	if(pessoaRequest.getNome() != null) {
    		pessoa.setNome(pessoaRequest.getNome());
    	}
    	pessoaRepository.save(pessoa);
    	PessoaResponse pessoaResponse = new PessoaResponse();
    	pessoaResponse.setNome(pessoa.getNome());
    	return pessoaResponse;
    }

    /**
     * Checa se um email existe na base de dados, caso exista, lança exceção.
     *
     * @param pessoaRequest PessoaCadastroRequest
     * @throws NegocioException
     * */
    private void checarEmailExistenteThrows(PessoaCadastroRequest pessoaRequest) {
        pessoaRepository.findByEmail(pessoaRequest.getEmail())
                .ifPresent((pessoa) -> {
                    throw new NegocioException("Já existe uma pessoa cadastrada com este EMAIL");
                });
    }

    /**
     * Checa se um CPF existe na base de dados, caso exista, lança exceção.
     *
     * @param pessoaRequest PessoaCadastroRequest
     * @throws NegocioException
     * */
    private void checarCPFExistenteThrows(PessoaCadastroRequest pessoaRequest) {
        pessoaRepository.findByCpf(pessoaRequest.getCpf())
                .ifPresent((pessoa) -> {
                    throw new NegocioException("Já existe uma pessoa cadastrada com esse CPF na base de dados");
                });
    }
    
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
     * Método responsável por verificar se uma pessoa logada na api é um participante
     *
     * @param pessoa
     * @return Participante
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
