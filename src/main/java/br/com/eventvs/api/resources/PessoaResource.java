package br.com.eventvs.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eventvs.api.dto.requests.PessoaCadastroRequest;
import br.com.eventvs.api.dto.responses.PessoaResponse;
import br.com.eventvs.core.security.EventvsSecurity;
import br.com.eventvs.domain.controller.GerenciarContaController;
import static br.com.eventvs.api.util.Paths.PATH_PESSOA;

@RestController
@RequestMapping(value = PATH_PESSOA)
public class PessoaResource {
	
	@Autowired
	private GerenciarContaController gerenciarContaController;
	
	@Autowired
    private EventvsSecurity eventvsSecurity;
	
	/**
	 * Alterar Dados de uma Pessoa
	 * @param PessoaCadastroRequest
	 * @return ResponseEntity<PessoaResponse>
	 */
	@PatchMapping()
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PessoaResponse> alterarDados(@RequestBody PessoaCadastroRequest pessoaCadastroRequest) {
		String email = eventvsSecurity.getPessoaEmail();
		PessoaResponse pessoaResponse = gerenciarContaController.alterarDados(pessoaCadastroRequest, email);
		return ResponseEntity.ok(pessoaResponse);
	}
	
}
