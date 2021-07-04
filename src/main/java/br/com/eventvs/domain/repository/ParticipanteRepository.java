package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository <Participante, Integer> {
	public Participante findByPessoa(Pessoa pessoa);
	public Optional <Participante> findByPessoaEmail(String email);
}
