package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Administrador;
import br.com.eventvs.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    Administrador findByPessoa(Pessoa pessoa);
}
