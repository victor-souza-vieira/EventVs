package br.com.eventvs.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.domain.model.Endereco;
import br.com.eventvs.domain.repository.EnderecoRepository;

@Service
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco salvarEndereco(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}
}
