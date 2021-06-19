package br.com.eventvs.domain.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.ProdutorRepository;

@Service
public class GerenciarContaProdutor {
	
	@Autowired
	private ProdutorRepository produtorRespository;
	
	
	/**
	 * Método para verificar se exite um produtor cadastrado no banco com um determinado id
	 * */
	public boolean getById(Integer id) {
		return produtorRespository.existsById(id);
		
	}
}
