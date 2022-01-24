package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

    @Override
    public boolean delete(int id) {
        // TODO NamedQuery
        return false;
    }

    @Override
    public List<Restaurant> getAll() {
        // TODO NamedQuery
        return null;
    }
}