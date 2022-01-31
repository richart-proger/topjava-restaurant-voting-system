package ru.javawebinar.restaurant_voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.restaurant_voting_system.data.RestaurantTestData;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void create() {
        Restaurant created = service.create(getNew());
        Integer newId = created.getId();
        Restaurant newRestaurant = getNew();
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
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RestaurantTestData.RESTAURANT_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getAll() {
        List<Restaurant> allRestaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(allRestaurants, getAllSorted());
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        updated = getUpdated();
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }
}