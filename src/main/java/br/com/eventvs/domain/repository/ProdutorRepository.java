package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutorRepository extends JpaRepository<Produtor,Integer> {
}
