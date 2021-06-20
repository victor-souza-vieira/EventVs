package br.com.eventvs.core.security.domain;

import br.com.eventvs.domain.model.Administrador;
import br.com.eventvs.domain.model.Participante;
import br.com.eventvs.domain.model.Pessoa;
import br.com.eventvs.domain.model.Produtor;
import br.com.eventvs.domain.repository.AdministradorRepository;
import br.com.eventvs.domain.repository.ParticipanteRepository;
import br.com.eventvs.domain.repository.PessoaRepository;
import br.com.eventvs.domain.repository.ProdutorRepository;
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

    @Autowired
    ProdutorRepository produtorRepository;

    @Autowired
    ParticipanteRepository participanteRepository;

    @Autowired
    AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        if(!pessoa.isPresent()){
            throw new UsernameNotFoundException("Pessoa não encontrada com email informado");
        }

        String role = "";

        Participante participante = participanteRepository.findByPessoa(pessoa.get());
        if (participante != null) role = "PARTICIPANTE";
        Produtor produtor = produtorRepository.findByPessoa(pessoa.get());
        if (produtor != null) role = "PRODUTOR";
        Administrador administrador = administradorRepository.findByPessoa(pessoa.get());
        if (administrador != null) role = "ADMINISTRADOR";


        return new AuthPessoa(pessoa.get(), role);
    }
}
