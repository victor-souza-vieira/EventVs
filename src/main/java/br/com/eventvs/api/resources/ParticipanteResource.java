package br.com.eventvs.api.resources;

import br.com.eventvs.api.dto.requests.ParticipanteCadastroRequest;
import br.com.eventvs.api.dto.responses.ParticipanteResponse;
import br.com.eventvs.domain.controller.CadastrarContaParticipanteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.eventvs.api.util.Paths.PATH_PARTICIPANTE;

@RestController
@RequestMapping(value = PATH_PARTICIPANTE)
public class ParticipanteResource {


}
