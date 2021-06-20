package br.com.eventvs.domain.controller;


import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuscarEventoController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public List<EventoResponse> listarTodosPublicados(String email){
        pessoaRepository.findByEmail(email)
                .orElseThrow(()->{
                    throw new EntidadeNaoEncontradaException("Usuário não está logado no sitema.");
                });

        List<Evento> eventos = eventoRepository.findAllByStatusEvento(StatusEvento.PUBLICADO);

        List<EventoResponse> eventoResponses = new ArrayList<>();
        eventos.forEach((e)->{
            EventoResponse eventoResponse = new EventoResponse();
            eventoResponse.setId(e.getId());
            eventoResponse.setNome(e.getNome());
            eventoResponse.setCategoria(e.getCategoria().getNome());
            eventoResponse.setStatusEvento(e.getStatusEvento().name());
            eventoResponse.setDescricao(e.getDescricao());
            eventoResponse.setDataHoraFim(e.getDataHoraFim());
            eventoResponse.setDataHoraInicio(e.getDataHoraInicio());
            eventoResponse.setEndereco(e.getEndereco());
            eventoResponse.setProdutor(e.getProdutor().getPessoa().getNome());
            eventoResponses.add(eventoResponse);
        });

        return eventoResponses;
    }


}
