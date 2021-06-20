package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.core.security.EventvsSecurity;
import br.com.eventvs.domain.controller.BuscarEventoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.eventvs.api.util.Paths.*;

@RestController
@RequestMapping(value = PATH_EVENTO)
public class EventoResource {

    @Autowired
    BuscarEventoController buscarEventoController;

    @Autowired
    EventvsSecurity eventvsSecurity;

    @GetMapping(value = PATH_EVENTOS_PUBLICADOS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosPublicados(){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosPublicados(email);
    }

    @GetMapping(value = PATH_EVENTOS_NAO_PUBLICADOS)
    @ResponseStatus(HttpStatus.OK)
    public List<EventoResponse> listarEventosNaoPublicados(){
        String email = eventvsSecurity.getPessoaEmail();
        return buscarEventoController.listarTodosNaoPublicados(email);
    }
}
