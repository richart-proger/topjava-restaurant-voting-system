package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurantMenu");

    public static final int RESTAURANT_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 42;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID, "Italian restaurant");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, "Japan restaurant");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2, "French restaurant");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID + 3, "Mexican restaurant");

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        return updated;
    }

    public static List<Restaurant> getAllSorted(){
        return List.of(
                RESTAURANT_3,
                RESTAURANT_1,
                RESTAURANT_2,
                RESTAURANT_4
        );
    }
}