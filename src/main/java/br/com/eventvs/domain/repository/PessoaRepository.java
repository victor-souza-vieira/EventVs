package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    public Optional<Pessoa> findByEmail(String email);

}
