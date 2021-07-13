package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.*;
import br.com.eventvs.domain.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.InscricaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GerenciarEventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private EnderecoController enderecoControler;

	@Autowired
	private GerenciarContaController gerenciarContaController;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private InscricaoRepository inscricaoRepository;


	/**
	 * Cria um evento no banco de Dados
	 * @param eventoRequest EventoRequest
	 * @param email String - Usuario deve ser um produtor
	 * @return EventoRespons
	 */
	public EventoResponse criarEvento(EventoRequest eventoRequest, String email){
		Pessoa pessoa = gerenciarContaController.login(email);

		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

		Endereco endereco = enderecoControler.salvarEndereco(eventoRequest.getEndereco());

		Evento evento = new Evento();
		evento.setId(eventoRequest.getId());
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
	
	/**
	 * Edita um Evento no banco de dados
	 * @param eventoID Integer
	 * @param eventoRequest - EventoRequest Request com os campos a serem alterados
	 * @param email - String Usuario deve ser o criador do evento e um produtor
	 * @return EventoResponse
	 */
	public EventoResponse editarEvento(Integer eventoID, EventoRequest eventoRequest, String email) {
		Pessoa pessoa = gerenciarContaController.login(email);
		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);
		
		Evento evento = eventoRepository.findById(eventoID)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		//Checagem de Regras de Negocio
		if(!evento.getProdutor().equals(produtor)) {
			throw new NegocioException("Esse evento não pertence ao produtor "+produtor.getPessoa().getNome());
		}
		if(evento.getStatusEvento().equals(StatusEvento.PUBLICADO)) {
			throw new NegocioException("Não é permitido editar um evento Publicado");
		}

		eventoRequest.setId(evento.getId());
		return criarEvento(eventoRequest, email);
	}
	
	/**
	 * Muda o status de um evento para Cancelado
	 * @param eventoID
	 * @param email
	 * @return boolean
	 */
	public void cancelarEvento(Integer eventoID, String email) {
		Pessoa pessoa = gerenciarContaController.login(email);
		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);
		
		Evento evento = eventoRepository.findById(eventoID)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		//Checagem de Regras de Negocio
		if(!evento.getProdutor().equals(produtor)) {
			throw new NegocioException("Esse evento não pertence ao produtor "+produtor.getPessoa().getNome());
		}
		if(!evento.getStatusEvento().equals(StatusEvento.PUBLICADO)) {
			throw new NegocioException("Não é permitido cancelar um evento Não Publicado");
		}
		evento.setStatusEvento(StatusEvento.CANCELADO);
		Optional<List<Inscricao>> inscricoes = inscricaoRepository.findByEvento(evento);
		if(inscricoes.isPresent()) {
			inscricoes.get().forEach((inscricao)-> {
				inscricao.setIsCancelada(true);
			});
		}
		evento = eventoRepository.save(evento);
	}

	/**
	 * Método responsável por publicar um evento
	 *
	 * @param email
	 * @param eventoId
	 * @return EventoResponse
	 * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
	 * */
	public EventoResponse publicarEvento(String email, Integer eventoId){
		Pessoa pessoa = gerenciarContaController.login(email);
		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

		Evento evento = eventoRepository.findByIdAndStatusEventoAndProdutor(eventoId, StatusEvento.CRIADO, produtor).orElseThrow(() -> {
			throw new EntidadeNaoEncontradaException("O produtor não possui evento com id " + eventoId + " a ser publicado.");
		});

		validaSePodeSerPublicado(evento);

		evento.setStatusEvento(StatusEvento.PUBLICADO);
		eventoRepository.save(evento);

		return preencherResponse(evento);
	}

	/**
	 * Método responsável por excluir evento criado
	 *
	 * @param email
	 * @param eventoId
	 * @return EventoResponse
	 * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
	 * */
	public void excluirEvento(String email, Integer eventoId){
		Pessoa pessoa = gerenciarContaController.login(email);
		Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

		Evento evento = eventoRepository.findByIdAndStatusEventoAndProdutor(eventoId, StatusEvento.CRIADO, produtor).orElseThrow(() -> {
			throw new NegocioException("O evento não pertence a este produtor.");
		});

		eventoRepository.delete(evento);
	}

	/**
	 * Valida se um evento pode ser publicado
	 *
	 * @param evento
	 * @return
	 * @throws NegocioException {@link NegocioException}
	 * */
	private void validaSePodeSerPublicado(Evento evento) {
		if(evento.getDataHoraFim() == null || evento.getDataHoraInicio() == null){
			throw new NegocioException("É necessário que o evento possua data de inicio e de fim para ser publicado.");
		}

		if(evento.getNome() == null){
			throw new NegocioException("É necessário que o evento possua um nome.");
		}
	}


	/**
	 * Preenche os dados de um evento em um EventoResponse
	 * @param evento Evento
	 * @return EventoResponse
	 */
	private EventoResponse preencherResponse(Evento evento) {

		EventoResponse eventoResponse = new EventoResponse();
		eventoResponse.setId(evento.getId());
		eventoResponse.setNome(evento.getNome());
		eventoResponse.setCategoria(evento.getCategoria());
		eventoResponse.setStatusEvento(evento.getStatusEvento().name());
		eventoResponse.setDescricao(evento.getDescricao());
		eventoResponse.setDataHoraFim(evento.getDataHoraFim());
		eventoResponse.setDataHoraInicio(evento.getDataHoraInicio());
		eventoResponse.setEndereco(evento.getEndereco());
		eventoResponse.setProdutor(evento.getProdutor().getPessoa().getNome());

		return eventoResponse;
	}
	
}	
