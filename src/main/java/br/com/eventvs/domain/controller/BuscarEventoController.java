package br.com.eventvs.domain.controller;


import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.CategoriaRepository;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuscarEventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LoginController loginController;

    @Autowired
    private ProdutorRepository produtorRepository;


    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado).
     *
     * @param email String
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicados(String email){
        loginController.login(email);

        List<Evento> eventos = eventoRepository.findAllByStatusEvento(StatusEvento.PUBLICADO);

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} criado)
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param categoriaId
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicadosPorCategoria(String email, Integer categoriaId){
        loginController.login(email);

        Categoria categoria = buscarCategoria(categoriaId);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndCategoria(StatusEvento.PUBLICADO, categoria);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem eventos publicados para a categoria informada.");
        }

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
        Pessoa pessoa = loginController.login(email);

        Produtor produtor = loginController.login(pessoa);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndProdutor(StatusEvento.CRIADO, produtor);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui nenhum evento não publicado.");
        }

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por retornar todos os eventos Não Publicados ({@link StatusEvento} criado)
     * que sejam de uma determinada categoria de um determinado Produtor.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param categoriaId
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosNaoPublicadosPorCategoria(String email, Integer categoriaId){
        Pessoa pessoa = loginController.login(email);

        Produtor produtor = loginController.login(pessoa);

        Categoria categoria = buscarCategoria(categoriaId);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndCategoriaAndProdutor(StatusEvento.CRIADO, categoria, produtor);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui nenhum evento não publicado com a categoria informada.");
        }

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por retornar todos os eventos Não Publicados ({@link StatusEvento} criado) por nome
     * de um determinado Produtor.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param eventoRequest EventoRequest
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosNaoPublicadosPorNome(String email, EventoRequest eventoRequest){
        Pessoa pessoa = loginController.login(email);

        Produtor produtor = loginController.login(pessoa);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndNomeContainsAndProdutor(StatusEvento.CRIADO, eventoRequest.getNome(), produtor);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui eventos não publicados com este nome.");
        }

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado) por nome.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param eventoRequest EventoRequest
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicadosPorNome(String email, EventoRequest eventoRequest){
        Pessoa pessoa = loginController.login(email);

        Produtor produtor = produtorRepository.findByPessoa(pessoa);

        List<Evento> eventos;
        if (produtor != null){
            eventos = eventoRepository.findAllByStatusEventoAndNomeContainsAndProdutor(StatusEvento.PUBLICADO, eventoRequest.getNome(), produtor);
        }else {
            eventos = eventoRepository.findAllByStatusEventoAndNomeContains(StatusEvento.PUBLICADO, eventoRequest.getNome());
        }

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem eventos publicados que contenham este nome.");
        }

        return preencherResponse(eventos);
    }

    /**
     * Método responsável por retornar todos os eventos Não Publicados ({@link StatusEvento} criado) por nome
     * de um determinado Produtor.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param eventoRequest EventoRequest
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosNaoPublicadosEntreDatas(String email, EventoRequest eventoRequest){
        Pessoa pessoa = loginController.login(email);

        Produtor produtor = loginController.login(pessoa);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndDataHoraInicioBetweenAndProdutor(StatusEvento.CRIADO, eventoRequest.getDataHoraInicio(), eventoRequest.getDataHoraFim() ,produtor);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui eventos não publicados com entre estas datas.");
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

    /**
     * Buscar uma categoria pelo id caso não encontre lança uma {@link NegocioException}
     *
     * @param categoriaId Integer
     * @return Categoria
     * @throws NegocioException
     * */
    private Categoria buscarCategoria(Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> {
                    throw new EntidadeNaoEncontradaException("Não existe categoria cadastrada com o id "+ categoriaId);
                });
    }


}
