package br.com.eventvs.domain.repository;

import br.com.eventvs.domain.model.Usuario;

import java.util.List;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);
    Usuario buscar(Long usuarioId);
    List<Usuario> listar();
    void remover(Usuario usuario);

}
