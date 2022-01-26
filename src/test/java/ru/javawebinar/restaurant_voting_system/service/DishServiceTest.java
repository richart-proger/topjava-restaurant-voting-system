package ru.javawebinar.restaurant_voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.restaurant_voting_system.data.DishTestData;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.DishTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void create() {
        Dish created = service.create(getNew());
        Integer newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    public void delete() {
        service.delete(DISH_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        Dish dish = service.get(DISH_ID);
        DISH_MATCHER.assertMatch(dish, DishTestData.DISH_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getAll() {
        List<Dish> allDishes = service.getAll();
        List<Dish> allTestDishes = Arrays.asList(DISH_1, DISH_2, DISH_3, DISH_4, DISH_5, DISH_6);
        DISH_MATCHER.assertMatch(allDishes, allTestDishes);
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        service.update(updated);
        updated = getUpdated();
        DISH_MATCHER.assertMatch(service.get(DISH_ID), updated);
    }
}