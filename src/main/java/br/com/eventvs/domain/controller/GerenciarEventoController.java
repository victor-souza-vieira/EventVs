package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
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
