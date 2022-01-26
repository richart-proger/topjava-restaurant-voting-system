package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vote get(int id, int userId) {
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUser().getId() == userId ? vote : null;
    }

    @Override
    public Vote save(Vote vote, int userId) {
        vote.setUser(em.getReference(User.class, userId));
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else if (get(vote.getId(), userId) == null) {
            return null;
        }
        return em.merge(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        // TODO NamedQuery
        return false;
    }

    @Override
    public Collection<Vote> getAll() {
        // TODO NamedQuery
        return null;
    }

    @Override
    public Collection<Vote> getAllByUserId(int userId) {
        // TODO NamedQuery
        return null;
    }
}