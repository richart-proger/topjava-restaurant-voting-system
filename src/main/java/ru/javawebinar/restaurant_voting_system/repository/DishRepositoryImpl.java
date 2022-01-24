package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.restaurant_voting_system.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DishRepositoryImpl implements DishRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Dish get(int id) {
        return em.find(Dish.class, id);
    }

    @Override
    public Dish save(Dish dish) {
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    @Override
    public boolean delete(int id) {
        // TODO NamedQuery
        return false;
    }

    @Override
    public List<Dish> getAll() {
        // TODO NamedQuery
        return null;
    }
}