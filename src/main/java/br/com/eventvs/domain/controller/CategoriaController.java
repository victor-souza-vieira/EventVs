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

    /**
     * Cadastra uma categoria
     *
     * @param categoriaRequest
     * @return Categoria
     * */
    public Categoria cadastrarCategoria(CategoriaRequest categoriaRequest){
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequest.getNome());
        categoria.setDescricao(categoriaRequest.getDescricao());

        return categoriaRepository.save(categoria);
    }

    /**
     * Retorna todas as categorias que possuem aquela descricao
     *
     * @param categoriaRequest
     * @return List<Categoria>
     * */
    public List<Categoria> listarCategoriaPorDescricao(CategoriaRequest categoriaRequest){
        return categoriaRepository.findAllByDescricaoContains(categoriaRequest.getDescricao());
    }

}
