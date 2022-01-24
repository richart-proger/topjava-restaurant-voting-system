package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import java.util.Set;

import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurantMenu");

    public static final int RESTAURANT_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 42;

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_ID, "Italian restaurant", Set.of(new Dish()));
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_ID + 1, "Japan restaurant", Set.of(new Dish()));
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_ID + 2, "French restaurant");

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", Set.of(new Dish(), new Dish()));
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant_1);
        updated.setName("UpdatedName");
        updated.setMenu(Set.of(new Dish(), new Dish(), new Dish()));
        return updated;
    }
}