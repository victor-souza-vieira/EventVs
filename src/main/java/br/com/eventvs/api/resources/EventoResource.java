package br.com.eventvs.api.resources;


import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.core.security.EventvsSecurity;
import br.com.eventvs.domain.controller.BuscarEventoController;
import br.com.eventvs.domain.controller.GerenciarEventoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.eventvs.api.util.Paths.*;


@RestController
@RequestMapping(value = PATH_EVENTO)
public class EventoResource {


	@Autowired
	private GerenciarEventoController gerenciarEventoControle;


    @Autowired
    private BuscarEventoController buscarEventoController;

    @Autowired
    private EventvsSecurity eventvsSecurity;

	@PostMapping
	public ResponseEntity<EventoResponse> criarEvento(@RequestBody EventoRequest eventoRequest) {
		String email = eventvsSecurity.getPessoaEmail();

		EventoResponse eventoResponse = gerenciarEventoControle.criarEvento(eventoRequest, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponse);

	}

    /**
     * Retorna todos os eventos publicados.
     *
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_PUBLICADOS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosPublicados(){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosPublicados(email);
    }


    /**
     * Retorna todos os eventos, não publicados, de um produtor
     *
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_NAO_PUBLICADOS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosNaoPublicados(){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosNaoPublicados(email);
    }


    /**
     * Retorna todos os eventos de uma determinada categoria, não publicados por produtor.
     *
     * @param categoriaId
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_NAO_PUBLICADOS_POR_CATEGORIA)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosNaoPublicadosPorCategoria(@PathVariable Integer categoriaId){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosNaoPublicadosPorCategoria(email, categoriaId);
    }


    /**
     * Retorna todos os eventos de uma determinada categoria, publicados por produtor.
     *
     * @param categoriaId
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_PUBLICADOS_POR_CATEGORIA)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosPublicadosPorCategoria(@PathVariable Integer categoriaId){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosPublicadosPorCategoria(email, categoriaId);
    }
}
