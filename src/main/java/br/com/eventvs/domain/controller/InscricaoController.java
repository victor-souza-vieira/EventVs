package br.com.eventvs.domain.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.api.dto.requests.InscricaoRequest;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Inscricao;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.InscricaoRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;

@Service
public class InscricaoController {
	
	@Autowired
	private InscricaoRepository inscricaoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ParticipanteRepository participanteRepository;

	@Autowired
	private GerenciarContaController gerenciarContaController;
	
	/**
     * Cadastra uma inscricao
     *
     * @param inscricaoRequest
     * @return Inscricao
     * @return null
     * */
	public Inscricao cadastrarInscricao(InscricaoRequest inscricaoRequest) {
		Participante participante = participanteRepository.findById(inscricaoRequest.getParticipante_id())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Participante não encontrado na base de dados."));
		Evento evento = eventoRepository.findById(inscricaoRequest.getEvento_id())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		
		//Checagem pra saber se a inscricao já existe
		Optional<Inscricao> inscricao_existe = inscricaoRepository.findByEventoAndParticipante(evento, participante);
		if(inscricao_existe.isPresent()) {
			throw new NegocioException("Participante já está inscrito nesse evento.");
		}
		Inscricao inscricao = new Inscricao();
		inscricao.setParticipante(participante);
		inscricao.setEvento(evento);
		inscricao.setIsCancelada(false);
		inscricao.setDataHora(LocalDateTime.now());
		return inscricaoRepository.save(inscricao);		
		
		
			
	}
	
	/**
	 * Retorna uma lista com as inscricoes do usuario
	 * @param email String
	 * @return List<Inscricao>
	 */
	public List<Inscricao> listarInscricoesPeloUsuario(String email){
		var pessoa = gerenciarContaController.login(email);
		var participante = gerenciarContaController.loginParticipante(pessoa);

		List<Inscricao> lista = inscricaoRepository.findByParticipante(participante)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Participante não possui inscrições."));
		return lista.stream().filter(inscricao -> !inscricao.getIsCancelada()).collect(Collectors.toList());
	}
	
	/**
	 * Retorna uma inscricao de um usuario
	 * @param inscricaoId Integer
	 * @param email String
	 * @return Optional <Inscricao>
	 */
	public Inscricao visualizarInscricao(Integer inscricaoId, String email) {
		 Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada na base de dados."));
		if(!inscricao.getParticipante().getPessoa().getEmail().equals(email)) {
			throw new NegocioException("Essa Inscrição não pertence ao participante.");
		}
		return inscricao;
	}
	
	/**
	 * Retorna uma lista com todas as inscricoes de um evento
	 * @param eventoId Integer
	 * @param email String
	 * @return Optional<List<Inscricao>>
	 */
	public List<Inscricao> visualizarParticipantes(Integer eventoId, String email) {
		Pessoa pessoa = gerenciarContaController.login(email);
		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);
		
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		if(!evento.getProdutor().equals(produtor)) {
			throw new NegocioException("Esse evento não pertence ao produtor "+produtor.getPessoa().getNome());
		}
		List<Inscricao> inscricoes = inscricaoRepository.findByEvento(evento)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não possui Participantes."));

		return inscricoes;
	}
	
	/**
	 * Cancela uma inscricao no banco de dados
	 * @param inscricaoId
	 * @param email
	 */
	public void cancelarInscricao(Integer inscricaoId, String email) {
		Pessoa pessoa = gerenciarContaController.login(email);
		
		Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição não encontrada na base de dados."));
		if(!inscricao.getParticipante().getPessoa().getEmail().equals(email)) {
			throw new NegocioException("Essa Inscrição não pertence ao participante.");
		}
		
		inscricao.setIsCancelada(true);
		inscricaoRepository.save(inscricao);
	}
			
	
}
