package ru.javawebinar.restaurant_voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.restaurant_voting_system.data.RestaurantTestData;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.util.JpaUtil;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.DishTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getRestaurantTo;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("restaurants").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void create() {
        Restaurant created = service.create(getNewRestaurant());
        Integer newId = created.getId();
        Restaurant newRestaurant = getNewRestaurant();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    public void delete() {
        service.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(RestaurantTestData.NOT_FOUND));
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RestaurantTestData.RESTAURANT_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(RestaurantTestData.NOT_FOUND));
    }

    @Test
    public void getAll() {
        List<Restaurant> allRestaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(allRestaurants, getAllSorted());
    }

    @Test
    public void update() {
        Restaurant updated = getUpdatedRestaurant();
        service.update(getRestaurantTo(updated));
        updated = getUpdatedRestaurant();
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }

    @Test
    public void getWithDishes() {
        Restaurant restaurant = service.getWithDishes(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT_1);
        DISH_MATCHER.assertMatch(restaurant.getMenu(),
                DISH_15, DISH_16, DISH_3, DISH_4, DISH_5);
    }

    @Test
    public void getWithDishesNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithDishes(1));
    }

    @Test
    public void getWithVotes() {
        Restaurant restaurant = service.getWithVotes(RESTAURANT_3.getId());
        RESTAURANT_MATCHER.assertMatch(restaurant, getVoted());
        VOTE_MATCHER.assertMatch(restaurant.getVotes(), VOTE_5, VOTE_4, VOTE_3);
    }

    @Test
    public void getWithVotesNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithVotes(1));
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "  ")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "a")));
    }
}