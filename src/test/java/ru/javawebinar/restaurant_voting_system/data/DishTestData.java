package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Dish;

import java.time.LocalDate;

import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurant");

    public static final int DISH_ID = START_SEQ + 5;
    public static final int NOT_FOUND = 42;

    public static final Dish DISH_1 = new Dish(DISH_ID, "Pizza", 600, LocalDate.now(), RESTAURANT_1);
    public static final Dish DISH_2 = new Dish(DISH_ID + 1, "Fettuccine", 700, LocalDate.now(), RESTAURANT_1);
    public static final Dish DISH_3 = new Dish(DISH_ID + 2, "Sushi Set", 1350, LocalDate.now(), RESTAURANT_2);
    public static final Dish DISH_4 = new Dish(DISH_ID + 3, "Sashimi Set", 1100, LocalDate.now(), RESTAURANT_2);
    public static final Dish DISH_5 = new Dish(DISH_ID + 4, "Ratatouille", 700, LocalDate.now(), RESTAURANT_3);
    public static final Dish DISH_6 = new Dish(DISH_ID + 5, "Onion soup", 500, LocalDate.now(), RESTAURANT_3);

    public static Dish getNew() {
        return new Dish(null, "New Dish", 999, LocalDate.now(), RESTAURANT_1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1);
        updated.setName("UpdatedName");
        updated.setPrice(777);
        updated.setDate(LocalDate.now().plusDays(1));
        updated.setRestaurant(RESTAURANT_3);
        return updated;
    }
}