package br.com.eventvs.domain.controller;


import br.com.eventvs.api.dto.requests.EventoRequest;
import br.com.eventvs.api.dto.responses.EventoResponse;
import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;
import br.com.eventvs.domain.model.*;
import br.com.eventvs.domain.repository.CategoriaRepository;
import br.com.eventvs.domain.repository.EventoRepository;
import br.com.eventvs.domain.repository.InscricaoRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuscarEventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private GerenciarContaController gerenciarContaController;

    @Autowired
    private ProdutorRepository produtorRepository;
    
    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado).
     *
     * @param email String
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicados(String email){
        gerenciarContaController.login(email);

        List<Evento> eventos = eventoRepository.findAllByStatusEvento(StatusEvento.PUBLICADO);

        return preencherResponse(eventos);
    }
    /**
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado menos os que o usuario já está inscrito).
     *
     * @param email String
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicadosFiltro(String email){
        Pessoa pessoa = gerenciarContaController.login(email);
        Participante participante = participanteRepository.findByPessoa(pessoa);
        List<Evento> eventos = eventoRepository.findAllByStatusEvento(StatusEvento.PUBLICADO);
        List<Evento> eventos_aux = new ArrayList<Evento>();
        eventos_aux.addAll(eventos);
        for(Evento evento : eventos_aux){
        	Optional<Inscricao> inscricao_existe = inscricaoRepository.findByEventoAndParticipante(evento, participante);
    		if(inscricao_existe.isPresent()) {
    			eventos.remove(evento);
    		}
        }	
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
        gerenciarContaController.login(email);

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
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

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
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

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
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

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
        Pessoa pessoa = gerenciarContaController.login(email);

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
     * Método responsável por retornar todos os eventos Publicados ({@link StatusEvento} publicado) entre determinadas datas.
     *
     * @throws EntidadeNaoEncontradaException {@link EntidadeNaoEncontradaException}
     * @throws NegocioException {@link NegocioException}
     * @param email String
     * @param eventoRequest EventoRequest
     * @return List of EventoResponse
     *
     * */
    public List<EventoResponse> listarTodosPublicadosEntreDatas(String email, EventoRequest eventoRequest){
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = produtorRepository.findByPessoa(pessoa);

        List<Evento> eventos;
        if (produtor != null){
            eventos = eventoRepository.findAllByStatusEventoAndDataHoraInicioBetweenAndProdutor(StatusEvento.PUBLICADO, eventoRequest.getDataHoraInicio(), eventoRequest.getDataHoraFim(), produtor);
        }else {
            eventos = eventoRepository.findAllByStatusEventoAndDataHoraInicioBetween(StatusEvento.PUBLICADO, eventoRequest.getDataHoraInicio(), eventoRequest.getDataHoraFim());
        }

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("Não existem eventos publicados entre essas datas.");
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
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = gerenciarContaController.loginProdutor(pessoa);

        List<Evento> eventos = eventoRepository.findAllByStatusEventoAndDataHoraInicioBetweenAndProdutor(StatusEvento.CRIADO, eventoRequest.getDataHoraInicio(), eventoRequest.getDataHoraFim() ,produtor);

        if (eventos.isEmpty()){
            throw new EntidadeNaoEncontradaException("O produtor não possui eventos não publicados com entre estas datas.");
        }

        return preencherResponse(eventos);
    }

    public EventoResponse listarPorId(String email, Integer eventoId){
        Pessoa pessoa = gerenciarContaController.login(email);

        Produtor produtor = produtorRepository.findByPessoa(pessoa);

        Evento evento;
        if (produtor != null){
            evento = eventoRepository.findByIdAndProdutor(eventoId, produtor)
                    .orElseThrow(() -> {
                        throw new EntidadeNaoEncontradaException("Não foi encontrado evento com este código");
                    });
        }else{
            evento = eventoRepository.findByIdAndStatusEvento(eventoId, StatusEvento.PUBLICADO)
                    .orElseThrow(() -> {
                        throw new EntidadeNaoEncontradaException("Não foi encontrado evento Publicado com este código");
                    });
        }

        return preencherResponse(evento);
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
     * Método responsável por preencher um EventoResponse
     * @param evento {@link Evento}
     * @return List of EventoResponse
     * */
    private EventoResponse preencherResponse(Evento evento) {

        EventoResponse eventoResponse = new EventoResponse();
        eventoResponse.setId(evento.getId());
        eventoResponse.setNome(evento.getNome());
        eventoResponse.setCategoria(evento.getCategoria().getNome());
        eventoResponse.setStatusEvento(evento.getStatusEvento().name());
        eventoResponse.setDescricao(evento.getDescricao());
        eventoResponse.setDataHoraFim(evento.getDataHoraFim());
        eventoResponse.setDataHoraInicio(evento.getDataHoraInicio());
        eventoResponse.setEndereco(evento.getEndereco());
        eventoResponse.setProdutor(evento.getProdutor().getPessoa().getNome());
        return eventoResponse;
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
