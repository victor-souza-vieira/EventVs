package br.com.eventvs.domain.controller;

import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

}
