package br.com.eventvs.domain.controller;

import br.com.eventvs.api.dto.requests.CategoriaRequest;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciarCategoriaController {

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
        List<Categoria> categorias = categoriaRepository.findAllByNomeContains(categoriaRequest.getNome());
        if (categorias.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não foram encontradas categorias que possuam este nome.");
        }
        return categorias;
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
        List<Categoria> categorias = categoriaRepository.findAllByDescricaoContains(categoriaRequest.getDescricao());
        if (categorias.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não foram encontradas categorias que possuam esta descrição.");
        }
        return categorias;
    }

    /**
     * Buscar uma categoria pelo id caso não encontre lança uma {@link NegocioException}
     *
     * @param categoriaId Integer
     * @return Categoria
     * @throws NegocioException
     * */
    public Categoria buscarCategoria(Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> {
                    throw new EntidadeNaoEncontradaException("Não existe categoria cadastrada com o id "+ categoriaId);
                });
    }

}
