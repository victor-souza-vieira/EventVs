package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.*;
import br.com.eventvs.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.repository.EventoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GerenciarEventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private EnderecoController enderecoControler;

	@Autowired
	private LoginController loginController;

	@Autowired
	private CategoriaRepository categoriaRepository;


	/*
	 * Cria um evento no banco de Dados
	 * @param EventoRequest eventoRequest
	 * @param String email - Usuario deve ser um produtor
	 * @return EventoRespons
	 */
	public EventoResponse criarEvento(EventoRequest eventoRequest, String email){
		Pessoa pessoa = loginController.login(email);

		Produtor produtor = loginController.login(pessoa);

		Endereco endereco = enderecoControler.salvarEndereco(eventoRequest.getEndereco());

		Evento evento = new Evento();
		evento.setNome(eventoRequest.getNome());
		evento.setDescricao(eventoRequest.getDescricao());
		evento.setDataHoraInicio(eventoRequest.getDataHoraInicio());
		evento.setDataHoraFim(eventoRequest.getDataHoraFim());
		evento.setCategoria(categoriaRepository.findById(eventoRequest.getCategoriaId()).orElseThrow(()->{throw new EntidadeNaoEncontradaException("Categoria não encontrada");}));
		evento.setStatusEvento(eventoRequest.getStatusEvento());
		evento.setProdutor(produtor);
		evento.setEndereco(endereco);

		evento = eventoRepository.save(evento);
		return preencherResponse(evento);
	}
	
	/*
	 * Edita um Evento no banco de dados
	 * @param Integer eventoID
	 * @param EventoRequest eventoRequest - Request com os campos a serem alterados
	 * @param String email - Usuario deve ser o criador do evento e um produtor
	 */
	public EventoResponse editarEvento(Integer eventoID, EventoRequest eventoRequest, String email) {
		Pessoa pessoa = loginController.login(email);
		Produtor produtor = loginController.login(pessoa);
		
		Evento evento = eventoRepository.findById(eventoID)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		//Checagem de Regras de Negocio
		if(!evento.getProdutor().equals(produtor)) {
			throw new NegocioException("Esse evento não pertence ao produtor "+produtor.getPessoa().getNome());
		}
		if(evento.getStatusEvento().equals(StatusEvento.PUBLICADO)) {
			throw new NegocioException("Não é permitido editar um evento Publicado");
		}
		
		if(!eventoRequest.getNome().isEmpty()) {
			evento.setNome(eventoRequest.getNome());
		}
		if(!(eventoRequest.getDataHoraFim() == null)) {
			evento.setDataHoraFim(eventoRequest.getDataHoraFim());
		}
		if(!(eventoRequest.getDataHoraInicio() == null)) {
			evento.setDataHoraInicio(eventoRequest.getDataHoraInicio());
		}
		if(!eventoRequest.getDescricao().isEmpty()) {
			evento.setDescricao(eventoRequest.getDescricao());
		}
		if(!(eventoRequest.getEndereco() == null)) {
			evento.setEndereco(eventoRequest.getEndereco());
		}
		if(!(eventoRequest.getStatusEvento() == null)) {
			evento.setStatusEvento(eventoRequest.getStatusEvento());
		}
		
		
		return preencherResponse(evento);
	}

	/*
	 * Preenche os dados de um evento em um EventoResponse
	 * @param Evento evento
	 * @return EventoResponse
	 */
	private EventoResponse preencherResponse(Evento evento) {

		EventoResponse eventoResponse = new EventoResponse();
		eventoResponse.setId(evento.getId());
		eventoResponse.setNome(evento.getNome());
		eventoResponse.setCategoria(evento.getCategoria().getNome());
		eventoResponse.setStatusEvento(evento.getStatusEvento().name());
		eventoResponse.setDescricao(evento.getDescricao());
		eventoResponse.setDataHoraFim(evento.getDataHoraFim());
		eventoResponse.setDataHoraInicio(evento.getDataHoraInicio());
		eventoResponse.setEndereco(evento.getEndereco());
		eventoResponse.setProdutor(evento.getProdutor().getPessoa().getNome());

		return eventoResponse;
	}
	
}	
