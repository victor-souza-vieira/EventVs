package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    List<Evento> findAllByStatusEvento(StatusEvento statusEvento);
}
