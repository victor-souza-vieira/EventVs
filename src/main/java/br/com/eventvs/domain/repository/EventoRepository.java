package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Evento;

import java.util.List;

public interface EventoRepository {

    Evento salvar(Evento evento);
    Evento buscar(Integer eventoId);
    List<Evento> listar();
    void remover(Evento evento);

}
