package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.to.DishTo;
import ru.javawebinar.restaurant_voting_system.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 42;

    public static final Dish DISH_1 = new Dish(DISH_ID, "Sushi Optimal Set", 1350, LocalDate.now().minusDays(5), RESTAURANT_2);
    public static final Dish DISH_2 = new Dish(DISH_ID + 1, "Sashimi Salmon Set", 1100, LocalDate.now().minusDays(5), RESTAURANT_2);

    public static final Dish DISH_3 = new Dish(DISH_ID + 2, "Pizza Margarita", 600, LocalDate.now().minusDays(3), RESTAURANT_1);
    public static final Dish DISH_4 = new Dish(DISH_ID + 3, "Fettuccine", 700, LocalDate.now().minusDays(3), RESTAURANT_1);
    public static final Dish DISH_5 = new Dish(DISH_ID + 4, "Lasagne", 950, LocalDate.now().minusDays(3), RESTAURANT_1);

    public static final Dish DISH_6 = new Dish(DISH_ID + 5, "Ratatouille", 700, LocalDate.now().minusDays(2), RESTAURANT_3);
    public static final Dish DISH_7 = new Dish(DISH_ID + 6, "Onion soup", 500, LocalDate.now().minusDays(2), RESTAURANT_3);

    public static final Dish DISH_8 = new Dish(DISH_ID + 7, "Tacos", 800, LocalDate.now().minusDays(2), RESTAURANT_4);
    public static final Dish DISH_9 = new Dish(DISH_ID + 8, "Burritos", 1050, LocalDate.now().minusDays(2), RESTAURANT_4);

    public static final Dish DISH_10 = new Dish(DISH_ID + 9, "Boeuf Bourguignon", 1850, LocalDate.now().minusDays(1), RESTAURANT_3);
    public static final Dish DISH_11 = new Dish(DISH_ID + 10, "Bouillabaisse", 1600, LocalDate.now().minusDays(1), RESTAURANT_3);

    public static final Dish DISH_12 = new Dish(DISH_ID + 11, "Burritos", 1050, LocalDate.now().minusDays(1), RESTAURANT_4);
    public static final Dish DISH_13 = new Dish(DISH_ID + 12, "Tamales", 1300, LocalDate.now().minusDays(1), RESTAURANT_4);
    public static final Dish DISH_14 = new Dish(DISH_ID + 13, "Quesadilla", 900, LocalDate.now().minusDays(1), RESTAURANT_4);

    public static final Dish DISH_15 = new Dish(DISH_ID + 14, "Pizza Red Diablo", 1000, LocalDate.now(), RESTAURANT_1);
    public static final Dish DISH_16 = new Dish(DISH_ID + 15, "Pappardelle", 800, LocalDate.now(), RESTAURANT_1);

    public static final Dish DISH_17 = new Dish(DISH_ID + 16, "Sushi Imperator Set", 1350, LocalDate.now(), RESTAURANT_2);
    public static final Dish DISH_18 = new Dish(DISH_ID + 17, "Sashimi Tuna Set", 1100, LocalDate.now(), RESTAURANT_2);

    public static final Dish DISH_19 = new Dish(DISH_ID + 18, "Ratatouille", 700, LocalDate.now(), RESTAURANT_3);
    public static final Dish DISH_20 = new Dish(DISH_ID + 19, "Onion soup", 500, LocalDate.now(), RESTAURANT_3);

    public static final Dish RESTAURANT_DISH = new Dish(DISH_3);

    public static final List<Dish> ALL_TEST_DISHES = List.of(
            DISH_20, DISH_18, DISH_17,
            DISH_16, DISH_15, DISH_19, DISH_14, DISH_13,
            DISH_12, DISH_11, DISH_10,
            DISH_8, DISH_7, DISH_6, DISH_9, DISH_5,
            DISH_4, DISH_3, DISH_2, DISH_1
    );

    public static Dish getNew() {
        return new Dish(null, "New Dish", 999, LocalDate.now(), RESTAURANT_1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1);
        updated.setName("UpdatedName");
        updated.setPrice(777);
        updated.setDate(LocalDate.now().plusDays(1));
        return updated;
    }

    public static MenuTo getNewMenuForToday() {
        DishTo dish1 = new DishTo(null, DISH_3.getName(), 333);
        DishTo dish2 = new DishTo(null, DISH_4.getName(), 555);
        return new MenuTo(null, RESTAURANT_1.getName(), List.of(dish1, dish2), LocalDate.now());
    }
}