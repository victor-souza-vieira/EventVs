package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.requests.CategoriaRequest;
import br.com.eventvs.domain.controller.CategoriaController;
import br.com.eventvs.domain.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.eventvs.api.util.Paths.*;

@RestController
@RequestMapping(value = PATH_CATEGORIA)
public class CategoriaResource {

    @Autowired
    private CategoriaController categoriaController;

    /**
     * Lista todas as categorias
     *
     * @return List<Categoria>
     * */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> listarCategorias(){
        return categoriaController.listarCategorias();
    }


    /**
     * Busca categorias que contenham o nome
     *
     * @param categoria
     * @return List<Categoria>
     * */
    @GetMapping
    @RequestMapping(value = PATH_BUSCAR_CATEGORIA_POR_NOME)
    public ResponseEntity<?> buscarCategoriasPorNome(@RequestBody CategoriaRequest categoria){
        List<Categoria> categorias = categoriaController.listarCategoriasPorNome(categoria);

        if(categorias.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    /**
     * Cadastra categoria
     *
     * @param categoriaRequest
     * @return Categoria
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria cadastrarCategoria(@RequestBody CategoriaRequest categoriaRequest){
        return categoriaController.cadastrarCategoria(categoriaRequest);
    }

    /**
     * Busca categorias que contenham aquela descricao
     *
     * @param categoria
     * @return List<Categoria>
     * */
    @GetMapping
    @RequestMapping(value = PATH_BUSCAR_CATEGORIA_POR_DESCRICAO)
    public ResponseEntity<?> buscarCategoriaPorDescricao(@RequestBody CategoriaRequest categoria) {
        List<Categoria> categorias = categoriaController.listarCategoriaPorDescricao(categoria);

        if (categorias.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }
}
