package ru.javawebinar.restaurant_voting_system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.restaurant_voting_system.data.RestaurantTestData;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

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
        RESTAURANT_MATCHER.assertMatch(restaurant, RestaurantTestData.restaurant_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getAll() {
        List<Restaurant> allRestaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(
                allRestaurants,
                restaurant_1,
                restaurant_2,
                restaurant_3
        );
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        updated = getUpdated();
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), updated);
    }
}