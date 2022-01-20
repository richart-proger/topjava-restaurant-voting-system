package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.restaurant_voting_system.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public boolean delete(int id) {
        // TODO NamedQuery
        return false;
    }

    @Override
    public User getByEmail(String email) {
        // TODO NamedQuery
        return null;
    }

    @Override
    public List<User> getAll() {
        // TODO NamedQuery
        return null;
    }
}