package br.com.eventvs.core.security.domain;

import br.com.eventvs.domain.enums.Situacao;
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
        Pessoa pessoa = pessoaRepository.findByEmail(email).orElseThrow(() -> {throw new UsernameNotFoundException("E-mail ou senha incorreto.");});

        String role = "";

        Participante participante = participanteRepository.findByPessoa(pessoa);
        if (participante != null){
            role = "PARTICIPANTE";
            return new AuthPessoa(pessoa, role);
        }

        Administrador administrador = administradorRepository.findByPessoa(pessoa);
        if (administrador != null){
            role = "ADMINISTRADOR";
            return new AuthPessoa(pessoa, role);
        }

        Produtor produtor = produtorRepository.findByPessoaAndSituacao(pessoa, Situacao.ACEITO).orElseThrow(()->{throw new UsernameNotFoundException("Sua conta ainda não foi aprovada por um administrador.");});
        if (produtor != null) {
            role = "PRODUTOR";
            return new AuthPessoa(pessoa, role);
        }
        return new AuthPessoa(pessoa, role);
    }
}
