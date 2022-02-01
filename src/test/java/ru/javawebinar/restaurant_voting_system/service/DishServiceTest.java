package ru.javawebinar.restaurant_voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.restaurant_voting_system.data.DishTestData;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.util.JpaUtil;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.DishTestData.NOT_FOUND;
import static ru.javawebinar.restaurant_voting_system.data.DishTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void create() {
        Dish created = service.create(getNew(), RESTAURANT_1.getId());
        Integer newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, RESTAURANT_1.getId()), newDish);
    }

    @Test
    public void delete() {
        service.delete(DISH_ID, RESTAURANT_2.getId());
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID, RESTAURANT_2.getId()));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT_1.getId()));
    }

    @Test
    public void get() {
        Dish dish = service.get(DISH_ID, RESTAURANT_2.getId());
        DISH_MATCHER.assertMatch(dish, DishTestData.DISH_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_1.getId()));
    }

    @Test
    public void getAll() {
        List<Dish> allDishes = service.getAll();
        DISH_MATCHER.assertMatch(allDishes, ALL_TEST_DISHES);
    }

    @Test
    void getMenuByDate() {
        DISH_MATCHER.assertMatch(service.getMenuByDate(RESTAURANT_ID, LocalDate.now()), DISH_15, DISH_16);
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT_2.getId());
        updated = getUpdated();
        DISH_MATCHER.assertMatch(service.get(DISH_ID, RESTAURANT_2.getId()), updated);
    }

    @Test
    void getMenuByRestaurantIdBetweenInclusive() {
        DISH_MATCHER.assertMatch(service.getMenuByRestaurantIdBetweenInclusive(
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), RESTAURANT_4.getId()), DISH_12, DISH_13, DISH_14, DISH_8, DISH_9
        );
    }

    @Test
    void getMenuByRestaurantIdBetweenWithNullDates() {
        DISH_MATCHER.assertMatch(service.getMenuByRestaurantIdBetweenInclusive(
                null, null, RESTAURANT_2.getId()), DISH_17, DISH_18, DISH_1, DISH_2
        );
    }

    @Test
    void getAllMenusBetweenInclusive() {
        DISH_MATCHER.assertMatch(service.getAllMenusBetweenInclusive(
                LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)), DISH_10, DISH_11, DISH_12, DISH_13, DISH_14, DISH_6, DISH_7, DISH_8, DISH_9
        );
    }

    @Test
    void getAllMenusBetweenWithNullDates() {
        DISH_MATCHER.assertMatch(service.getAllMenusBetweenInclusive(null, null), ALL_TEST_DISHES);
    }

    @Test
    public void getWithRestaurant() {
        Dish restaurantDish = service.getWithRestaurant(RESTAURANT_DISH.getId(), RESTAURANT_ID);
        DISH_MATCHER.assertMatch(restaurantDish, RESTAURANT_DISH);
        RESTAURANT_MATCHER.assertMatch(restaurantDish.getRestaurant(), RESTAURANT_1);
    }

    @Test
    public void getWithRestaurantNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithRestaurant(1, RESTAURANT_ID));
    }
}