package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.CategoriaRequest;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Retorna todas as categorias
     *
     * @return List<Categoria>
     * */
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    /**
     * Retorna todas as categorias que possuem aquele nome
     *
     * @param categoriaRequest
     * @return List<Categoria>
     * */
    public List<Categoria> listarCategoriasPorNome(CategoriaRequest categoriaRequest){
        return categoriaRepository.findAllByNomeContains(categoriaRequest.getNome());
    }

}
