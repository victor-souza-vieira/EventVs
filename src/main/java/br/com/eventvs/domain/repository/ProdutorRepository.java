package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutorRepository extends JpaRepository<Produtor,Integer> {
    Produtor findByPessoa(Pessoa pessoa);

    Optional<Produtor> findByPessoaId(Integer pessoaId);
}
