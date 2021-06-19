package br.com.eventvs.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.api.exceptionhandler.ApiExceptionHandler;
import br.com.eventvs.domain.controller.GerenciarEventoController;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.repository.EventoRepository;

import static br.com.eventvs.api.util.Paths.PATH_EVENTO;

import java.net.http.HttpHeaders;
import java.util.List;

@RestController
@RequestMapping(value = PATH_EVENTO)
public class EventoResource {
	
	@Autowired
	private GerenciarEventoController gerenciarEventoControle;
	
	@PostMapping
	public ResponseEntity<EventoResponse> criarEvento(@RequestBody EventoRequest eventoRequest) {
		
		EventoResponse eventoResponse = gerenciarEventoControle.criarEvento(eventoRequest);
        if(eventoResponse != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponse);
        }
        return ResponseEntity.badRequest().build();
	}
	

}
