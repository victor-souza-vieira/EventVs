package br.com.eventvs.domain.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.eventvs.api.dto.requests.InscricaoRequest;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Inscricao;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
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
	private PessoaRepository pessoaRepository;
	
	/**
     * Cadastra uma inscricao
     *
     * @param InscricaoRequest
     * @return Inscricao
     * @return null
     * */
	public Inscricao cadastrarInscricao(InscricaoRequest inscricaoRequest) {
		Optional <Participante> participante = participanteRepository.findById(inscricaoRequest.getParticipante_id());
		Optional <Evento> evento = eventoRepository.findById(inscricaoRequest.getEvento_id());
		if(participante.isPresent() && evento.isPresent()) {
			//Checagem pra saber se a inscricao já existe
			List<Inscricao> inscricoes = inscricaoRepository.findByParticipante(participante.get());
			for(int i =0; i< inscricoes.size();i++) {
				if(inscricoes.get(i).getEvento().equals(evento.get())) {
					return null;
				}
			}
			
			Inscricao inscricao = new Inscricao();
			inscricao.setParticipante(participante.get());
			inscricao.setEvento(evento.get());
			inscricao.setDataHora(LocalDateTime.now());
			return inscricaoRepository.save(inscricao);
		}
		return null;
	}
	
	/*
	 * Retorna uma lista com as inscricoes do usuario
	 * @param String email
	 * @return List<Inscricao>
	 */
	public List<Inscricao> listarInscricoesPeloUsuario(String email){
		List<Inscricao> lista = null;
		Optional<Participante> p  = participanteRepository.findByPessoaEmail(email);
		if(p.isPresent()) {
			lista = inscricaoRepository.findByParticipante(p.get());
		}        
		return lista;
	}
	
	/*
	 * Retorna uma inscricao de um usuario
	 * @param Integer inscricaoId 
	 * @param String email
	 * @return Optional <Inscricao>
	 */
	public Optional <Inscricao> visualizarInscricao(Integer inscricaoId, String email) {
		Optional <Inscricao> inscricao = inscricaoRepository.findById(inscricaoId);
		if(inscricao.isPresent()) {
			if(inscricao.get().getParticipante().getPessoa().getEmail().equals(email)) {
				return inscricao;
			}else {
				return inscricao.ofNullable(null);
			}
		}
		return inscricao;
	}
	
	/*
	 * Retorna uma lista com todas as inscricoes de um evento
	 * @param Integer eventoId 
	 * @param String email
	 * @return Optional<List<Inscricao>>
	 */
	public Optional<List<Inscricao>> visualizarParticipantes(Integer eventoId, String email) {
		Optional<Evento> evento = eventoRepository.findById(eventoId);
		Optional <List<Inscricao>> inscricoes = null;
		if(evento.isPresent()) {
			Evento existente = evento.get();
			inscricoes = Optional.ofNullable(inscricaoRepository.findByEvento(existente));
			return inscricoes;
		}		
		return inscricoes.ofNullable(null);
		
	}
			
	
}
