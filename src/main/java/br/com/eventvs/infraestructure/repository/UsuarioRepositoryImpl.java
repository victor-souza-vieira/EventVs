package br.com.eventvs.infraestructure.repository;

import br.com.eventvs.domain.model.Usuario;
import br.com.eventvs.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @PersistenceContext
    private EntityManager manager;


    @Transactional
    @Override
    public Usuario salvar(Usuario usuario) {
        return manager.merge(usuario);
    }

    @Override
    public Usuario buscar(Long usuarioId) {
        return manager.find(Usuario.class, usuarioId);
    }

    @Override
    public List<Usuario> listar() {
        return manager.createQuery("from Usuario", Usuario.class).getResultList();
    }

    @Transactional
    @Override
    public void remover(Usuario usuario) {
        usuario = buscar(usuario.getId());
        manager.remove(usuario);
    }
}
