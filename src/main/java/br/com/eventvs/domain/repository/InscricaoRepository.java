package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Inscricao;
import br.com.eventvs.domain.model.Participante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao,Integer> {
	public List<Inscricao> findByParticipante(Participante participante);
}
