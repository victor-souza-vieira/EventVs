package br.com.eventvs.core.security.domain;

import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        if(!pessoa.isPresent()){
            throw new UsernameNotFoundException("Pessoa não encontrada com email informado");
        }

        return new AuthPessoa(pessoa.get());
    }
}
