package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_SORTED, Restaurant.class).getResultList();
    }
}