package br.com.eventvs.infraestructure.repository;

import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.repository.EventoRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EventoRepositoryImpl implements EventoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public Evento salvar(Evento evento) {
        return manager.merge(evento);
    }

    @Override
    public Evento buscar(Integer eventoId) {
        return manager.find(Evento.class, eventoId);
    }

    @Override
    public List<Evento> listar() {
        return manager.createQuery("from Evento", Evento.class).getResultList();
    }

    @Transactional
    @Override
    public void remover(Evento evento) {
        evento = buscar(evento.getId());
        manager.remove(evento);
    }
}
