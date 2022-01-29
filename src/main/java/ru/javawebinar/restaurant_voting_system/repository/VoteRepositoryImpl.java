package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepositoryImpl implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vote get(int id, int userId) {
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUser().getId() == userId ? vote : null;
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Vote> getAll() {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .getResultList();
    }

    @Override
    public List<Vote> getAllByUserId(int userId) {
        return em.createNamedQuery(Vote.ALL_BY_USER_ID_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Vote> getBetweenPeriod(LocalDate startDate, LocalDate endDate, int userId) {
        return em.createNamedQuery(Vote.GET_BETWEEN, Vote.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}