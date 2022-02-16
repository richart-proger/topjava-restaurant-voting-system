package ru.javawebinar.restaurant_voting_system.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.restaurant_voting_system.data.DishTestData;
import ru.javawebinar.restaurant_voting_system.data.UserTestData;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javawebinar.restaurant_voting_system.data.DishTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;

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

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(getUserTo(UserTestData.USER), "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}