package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao,Integer> {
}
