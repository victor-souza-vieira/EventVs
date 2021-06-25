package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

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

}
