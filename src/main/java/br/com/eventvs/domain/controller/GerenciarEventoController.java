package br.com.eventvs.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.model.Endereco;
import br.com.eventvs.domain.model.Evento;
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
	private GerenciarContaProdutor gerenciarProdutorControler;

	
	
	/**
     * Cria um evento
     *
     * @param evento
     * @return EventoResponse
     * @return null
     * */
	public EventoResponse criarEvento(EventoRequest eventoRequest) {
		
		boolean produtor = gerenciarProdutorControler.getById(eventoRequest.getProdutor().getId());
		
		if (produtor) {
			
			Evento evento = new Evento();
			Endereco endereco = enderecoControler.salvarEndereco(eventoRequest.getEndereco());
			
			evento.setNome(eventoRequest.getNome());
			evento.setDescricao(eventoRequest.getDescricao());
			evento.setDataHoraInicio(eventoRequest.getDataHoraInicio());
			evento.setDataHoraFim(eventoRequest.getDataHoraFim());
			evento.setCategoria(eventoRequest.getCategoria());
			evento.setStatusEvento(eventoRequest.getStatusEvento());
			evento.setProdutor(eventoRequest.getProdutor());
			evento.setEndereco(endereco);
		
			evento =  eventoRepository.save(evento);
			
			EventoResponse eventoResponse = new EventoResponse();
			evento.setNome(evento.getNome());
			evento.setDescricao(evento.getDescricao());
			evento.setDataHoraInicio(evento.getDataHoraInicio());
			evento.setDataHoraFim(evento.getDataHoraFim());
			evento.setCategoria(evento.getCategoria());
			evento.setStatusEvento(evento.getStatusEvento());
			evento.setProdutor(evento.getProdutor());
			evento.setEndereco(evento.getEndereco());
			
			return eventoResponse;
			
		} else {
			//log.warn("Não existe produtor cadastrado com esse id");	
			return null;
		}
	}
	
}	
