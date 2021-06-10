package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
}
