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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.validation.Valid;

import static br.com.eventvs.api.util.Paths.*;


@RestController
@RequestMapping(value = PATH_EVENTO)
@CrossOrigin(methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT})
public class EventoResource {


	@Autowired
	private GerenciarEventoController gerenciarEventoControle;


    @Autowired
    private BuscarEventoController buscarEventoController;

    @Autowired
    private EventvsSecurity eventvsSecurity;
    
    /**
     * Cadastra um evento
     * @param eventoRequest EventoRequest
     * @return ResponseEntity<EventoResponse>
     */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EventoResponse> criarEvento(@Valid @RequestBody EventoRequest eventoRequest) {
		String email = eventvsSecurity.getPessoaEmail();

		EventoResponse eventoResponse = gerenciarEventoControle.criarEvento(eventoRequest, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponse);

	}
	
	/**
	 * Edita um Evento
	 * @param eventoId Integer
	 * @param eventoRequest EventoRequest
	 * @return ResponseEntity<EventoResponse>
	 */
	@PatchMapping(value = PATH_EVENTO_ID)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EventoResponse> editarEvento(@PathVariable Integer eventoId, @RequestBody EventoRequest eventoRequest) {
		String email = eventvsSecurity.getPessoaEmail();
		
		EventoResponse eventoResponse = gerenciarEventoControle.editarEvento(eventoId, eventoRequest, email);
		
		return ResponseEntity.ok(eventoResponse);
	}
	
	/**
	 * Cancela um evento
	 * @param eventoId
	 * @return ResponseEntity
	 */
	@PatchMapping(value = PATH_EVENTO_ID_CANCELAR)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity cancelarEvento(@PathVariable Integer eventoId) {
		String email = eventvsSecurity.getPessoaEmail();
		gerenciarEventoControle.cancelarEvento(eventoId, email);
		return ResponseEntity.ok().build();
	}

	/**
     * Publica um evento
     * @param eventoId
     * @return EventoResponse
     * */
	@PatchMapping(value = PATH_EVENTO_ID_PUBLICAR)
    @ResponseStatus(HttpStatus.OK)
	public EventoResponse publicarEvento(@PathVariable Integer eventoId){
        String email = eventvsSecurity.getPessoaEmail();
        return gerenciarEventoControle.publicarEvento(email, eventoId);
    }

    /**
     * Excluir evento criado
     * @param eventoId
     * @return EventoResponse
     * */
	@DeleteMapping(value = PATH_EVENTO_ID)
    @ResponseStatus(HttpStatus.OK)
	public void excluirEvento(@PathVariable Integer eventoId){
        String email = eventvsSecurity.getPessoaEmail();
        gerenciarEventoControle.excluirEvento(email, eventoId);
    }

    /**
     * Retorna um evento filtrado pelo ID
     *
     * @return EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTO_ID)
    @ResponseStatus(HttpStatus.OK)
    public EventoResponse buscarPorId(@PathVariable Integer eventoId){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarPorId(email, eventoId);
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
     * Retorna todos os eventos de uma determinada categoria, não publicados por produtor.
     *
     * @param eventoRequest EventoRequest
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_NAO_PUBLICADOS_POR_NOME)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosNaoPublicadosPorNome(@RequestBody EventoRequest eventoRequest){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosNaoPublicadosPorNome(email, eventoRequest);
    }

    /**
     * Retorna todos os eventos de uma determinada categoria, não publicados por produtor.
     *
     * @param request Map
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_NAO_PUBLICADOS_ENTRE_DATAS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosNaoPublicadosEntreDatas(@RequestParam Map<String, String> request){
        String email = eventvsSecurity.getPessoaEmail();
        var eventoRequest = new EventoRequest();

        eventoRequest.setDataHoraInicio(LocalDateTime.parse(request.get(request.keySet().toArray()[0])));
        eventoRequest.setDataHoraFim(LocalDateTime.parse(request.get(request.keySet().toArray()[1])));

        return buscarEventoController.listarTodosNaoPublicadosEntreDatas(email, eventoRequest);
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

    /**
     * Retorna todos os eventos por nome.
     *
     * @param eventoRequest
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_PUBLICADOS_POR_NOME)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosPublicadosPorNome(@RequestBody EventoRequest eventoRequest){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosPublicadosPorNome(email, eventoRequest);
    }

    /**
     * Retorna todos os eventos entre um intervalo de datas.
     *
     * @param request
     * @return List of EventoResponse {@link EventoResponse}
     * */
    @GetMapping(value = PATH_EVENTOS_PUBLICADOS_ENTRE_DATAS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosPublicadosEntreDatas(@RequestParam Map<String, String> request){
        String email = eventvsSecurity.getPessoaEmail();
        var eventoRequest = new EventoRequest();

        eventoRequest.setDataHoraInicio(LocalDateTime.parse(request.get(request.keySet().toArray()[0])));
        eventoRequest.setDataHoraFim(LocalDateTime.parse(request.get(request.keySet().toArray()[1])));

        return buscarEventoController.listarTodosPublicadosEntreDatas(email, eventoRequest);
    }
}
