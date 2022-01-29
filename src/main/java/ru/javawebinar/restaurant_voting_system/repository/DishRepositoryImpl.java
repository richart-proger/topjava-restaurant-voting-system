package ru.javawebinar.restaurant_voting_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishRepositoryImpl implements DishRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Dish get(int id) {
        return em.find(Dish.class, id);
    }

    @Override
    @Transactional
    public Dish save(Dish dish) {
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Dish.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public List<Dish> getAll() {
        return em.createNamedQuery(Dish.ALL_SORTED, Dish.class).getResultList();
    }

    @Override
    public List<Dish> getMenu(int restaurantId, LocalDate date) {
        return em.createNamedQuery(Dish.GET_MENU, Dish.class)
                .setParameter("id", restaurantId)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Dish> getMenuByRestaurantIdBetweenPeriod(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return em.createNamedQuery(Dish.GET_MENU_BY_RESTAURANT_ID_BETWEEN, Dish.class)
                .setParameter("restaurantId", restaurantId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<Dish> getAllMenusBetweenPeriod(LocalDate startDate, LocalDate endDate) {
        return em.createNamedQuery(Dish.GET_ALL_MENUS_BETWEEN, Dish.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}