package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findAllByStatusEvento(StatusEvento statusEvento);
    List<Evento> findAllByStatusEventoAndProdutor(StatusEvento statusEvento, Produtor produtor);
    List<Evento> findAllByStatusEventoAndCategoriaAndProdutor(StatusEvento statusEvento, Categoria categoria, Produtor produtor);
    List<Evento> findAllByStatusEventoAndCategoria(StatusEvento statusEvento, Categoria categoria);
    List<Evento> findAllByStatusEventoAndNomeContainsAndProdutor(StatusEvento statusEvento, String nome, Produtor produtor);
    List<Evento> findAllByStatusEventoAndDataHoraInicioBetweenAndProdutor(StatusEvento statusEvento, LocalDateTime dataInicio, LocalDateTime dataFim, Produtor produtor);
    Optional<Evento> findByIdAndStatusEventoAndProdutor(Integer id, StatusEvento statusEvento, Produtor produtor);
    List<Evento> findAllByStatusEventoAndNomeContains(StatusEvento statusEvento, String nome);
}
