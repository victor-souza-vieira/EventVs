package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Inscricao;
import br.com.eventvs.domain.model.Participante;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao,Integer> {
	public Optional <List<Inscricao>> findByParticipante(Participante participante);
	public Optional <List<Inscricao>> findByEvento(Evento evento);
	public Optional <Inscricao> findByEventoAndParticipante(Evento evento, Participante participante);
}
