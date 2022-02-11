package ru.javawebinar.restaurant_voting_system.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.restaurant_voting_system.data.DishTestData;
import ru.javawebinar.restaurant_voting_system.model.Dish;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.data.DishTestData.*;

class JsonUtilTest {
    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(DISH_1);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        DISH_MATCHER.assertMatch(dish, DISH_1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(ALL_TEST_DISHES);
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        DISH_MATCHER.assertMatch(dishes, DishTestData.ALL_TEST_DISHES);
    }
}