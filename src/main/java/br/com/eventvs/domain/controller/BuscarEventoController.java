package br.com.eventvs.domain.controller;


import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuscarEventoController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ProdutorRepository produtorRepository;

    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado).
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @param email String
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicados(String email){
        pessoaRepository.findByEmail(email)
                .orElseThrow(()->{
                    throw new EntidadeNaoEncontradaException("Usuário não está logado no sistema.");
                });

        List<Evento> eventos = eventoRepository.findAllByStatusEvento(StatusEvento.PUBLICADO);

        return preencherResponse(eventos);
    }


    /**
     * Método responsável por retornar todos os eventos Não Publicados ({@link StatusEvento} criado)
     * de um determinado Produtor.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosNaoPublicados(String email){
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(()->{
                    throw new EntidadeNaoEncontradaException("Usuário não está logado no sistema.");
                });

        Produtor produtor = produtorRepository.findByPessoaId(pessoa.getId())
                .orElseThrow(() -> {
                    throw new NegocioException("O usuário não é um produtor de eventos.");
                });


        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndProdutor(StatusEvento.CRIADO, produtor).get();

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui nenhum evento não publicado.");
        }

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por preencher um EventoResponse
     * @param eventos List
     * @return List of EventoResponse
     * */
    private List<EventoResponse> preencherResponse(List<Evento> eventos) {
        List<EventoResponse> eventoResponses = new ArrayList<>();
        eventos.forEach((e)-> {
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
