package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Feedback;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ciandt.feedfront.utils.PersistenceUtil.getEntityManagerFactory;

public class FeedbackDAO implements DAO<Feedback> {
    private EntityManager entityManager;

    public FeedbackDAO() {
        entityManager = getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<Feedback> listar() {
        return entityManager.createQuery("SELECT feed FROM Feedback feed").getResultList();
    }

    @Override
    public Optional<Feedback> buscar(long id) {
        return Optional.ofNullable(entityManager.find(Feedback.class, id));
    }

    @Override
    public Feedback salvar(Feedback feedback) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(feedback);
        transaction.commit();
        return feedback;
    }

    @Override
    public boolean apagar(long id) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(buscar(id).get());
        transaction.commit();
        return true;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
