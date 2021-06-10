package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository <Participante, Integer> {
}
