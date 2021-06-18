package br.com.eventvs.api.resources;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eventvs.api.dto.requests.CategoriaRequest;
import br.com.eventvs.api.dto.requests.InscricaoRequest;
import br.com.eventvs.api.dto.responses.InscricaoResponse;
import br.com.eventvs.core.security.EventvsSecurity;
import br.com.eventvs.domain.controller.InscricaoController;
import br.com.eventvs.domain.model.Inscricao;

import static br.com.eventvs.api.util.Paths.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = PATH_INSCRICAO)
@CrossOrigin(methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT})
public class InscricaoResource {
	
	@Autowired
	private InscricaoController inscricaoController;
		
	@Autowired
    private EventvsSecurity eventvsSecurity;
	
	@Autowired
	ModelMapper modelMapper;
	
	/*
	 * Cadastra uma inscrição
	 * @param InscricaoRequest inscricaoRequest
	 * @return ResponseEntity<InscricaoResponse>
	 */
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<InscricaoResponse> realizarInscricao(@RequestBody InscricaoRequest inscricaoRequest) {
		Inscricao inscricao = inscricaoController.cadastrarInscricao(inscricaoRequest);
		if(inscricao!= null) {
			return ResponseEntity.ok(toModel(inscricao));
		}
		return ResponseEntity.badRequest().build();
	}
	/**
     * Lista as Inscrições de um evento
     * @param eventoId
     * @return List<InscricaoResponse>
     * */
	@GetMapping(value = PATH_VIZUALIZAR_PARTICIPANTES)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<InscricaoResponse>> visualizarParticipantes(@PathVariable Integer eventoId) {
		String user = eventvsSecurity.getPessoaEmail();
		Optional<List<Inscricao>> inscricoes = inscricaoController.visualizarParticipantes(eventoId, user);
		if(inscricoes.isPresent()) {
			return ResponseEntity.ok(toCollectionModel(inscricoes.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	/**
     * Busca uma inscrição
     * @param inscricaoId
     * @return InscricaoResponse
     * */
	@GetMapping(value = PATH_BUSCAR_INSCRICAO_ID)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<InscricaoResponse> visualizarInscricao(@PathVariable Integer inscricaoId) {
		String user = eventvsSecurity.getPessoaEmail();
		Optional<Inscricao> inscricao = inscricaoController.visualizarInscricao(inscricaoId, user);
		if(inscricao.isPresent()) {
			return ResponseEntity.ok(toModel(inscricao.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	/**
     * Lista as inscrições de um usuário
     * @return List<InscricaoResponse>
     * */
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<InscricaoResponse> listarInscricao() {
		String user = eventvsSecurity.getPessoaEmail();
		List<Inscricao> inscricoes = inscricaoController.listarInscricoesPeloUsuario(user);
		
		return toCollectionModel(inscricoes);
	}
	
	/**
     * Mapeamento de uma Inscricao em InscricaoResponse
     * @param Inscricao
     * @return InscricaoResponse
     * */
	private InscricaoResponse toModel(Inscricao inscricao) {
		return modelMapper.map(inscricao, InscricaoResponse.class);
	}
	
	/**
     * Mapeamento de uma lista de Inscricoes em uma lista de InscricaoResponse
     * @param List<Inscricao>
     * @return List<InscricaoResponse>
     * */
	private List<InscricaoResponse> toCollectionModel(List<Inscricao> inscricoes){
		return inscricoes.stream()
				.map(inscricao -> toModel(inscricao))
				.collect(Collectors.toList());
	}
	
}
