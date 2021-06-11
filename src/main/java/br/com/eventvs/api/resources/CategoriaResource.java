package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.requests.CategoriaRequest;
import br.com.eventvs.domain.controller.CategoriaController;
import br.com.eventvs.domain.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.eventvs.api.util.Paths.PATH_BUSCAR_CATEGORIA_POR_NOME;
import static br.com.eventvs.api.util.Paths.PATH_CATEGORIA;

@RestController
@RequestMapping(value = PATH_CATEGORIA)
public class CategoriaResource {

    @Autowired
    private CategoriaController categoriaController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> listarCategorias(){
        return categoriaController.listarCategorias();
    }


    /**
     * Busca categorias pelo nome ou parte dele
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

}
