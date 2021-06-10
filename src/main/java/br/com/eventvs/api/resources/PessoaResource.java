package br.com.eventvs.api.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static br.com.eventvs.api.util.Paths.PATH_PESSOA;

@RestController
@RequestMapping(value = PATH_PESSOA)
public class PessoaResource {
}
