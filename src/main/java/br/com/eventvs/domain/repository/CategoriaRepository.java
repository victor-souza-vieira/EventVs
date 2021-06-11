package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    public List<Categoria> findAllByNomeContains(String nome);

}
