package br.com.eventvs.domain.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.eventvs.domain.model.Inscricao;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.InscricaoRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;

@Service
public class InscricaoController {
	
	@Autowired
	private InscricaoRepository inscricaoRepository;
	
	@Autowired
	private ParticipanteRepository participanteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public List<Inscricao> listarInscricoesPeloUsuario(String email){
		List<Inscricao> lista = null;
		Optional<Participante> p  = participanteRepository.findByPessoaEmail(email);
		if(p.isPresent()) {
			lista = inscricaoRepository.findByParticipante(p.get());
		}        
		return lista;
	}
	
	public Optional <Inscricao> visualizarInscricao(Integer inscricaoId, String email) {
		Optional <Inscricao> inscricao = inscricaoRepository.findById(inscricaoId);
		if(inscricao.isPresent()) {
			if(inscricao.get().getParticipante().getPessoa().getEmail().equals(email)) {
				return inscricao;
			}
		}
		return null;
	}
			
	
}
