package br.com.eventvs.domain.controller;

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
	
	public EventoResponse editarEvento(Integer eventoID, EventoRequest eventoRequest, String email) {
		Pessoa pessoa = loginController.login(email);
		Produtor produtor = loginController.login(pessoa);
		
		Evento evento = eventoRepository.findById(eventoID)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado na base de dados."));
		
		if(!evento.getProdutor().equals(produtor)) {
			throw new NegocioException("Esse evento não pertence ao produtor "+produtor.getPessoa().getNome());
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
