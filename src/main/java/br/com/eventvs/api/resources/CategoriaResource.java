package br.com.eventvs.api.resources;

import br.com.eventvs.domain.controller.CategoriaController;
import br.com.eventvs.domain.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.eventvs.api.util.Paths.PATH_CATEGORIA;

@RestController
@RequestMapping(value = PATH_CATEGORIA)
public class CategoriaResource {

    @Autowired
    CategoriaController categoriaController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> listarCategorias(){
        return categoriaController.listarCategorias();
    }

}
