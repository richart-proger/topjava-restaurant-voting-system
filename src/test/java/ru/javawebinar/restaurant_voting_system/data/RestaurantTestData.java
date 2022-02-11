package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;
import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "restaurantMenu", "votes");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "restaurantMenu", "votes");

    public static final int RESTAURANT_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 42;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID, "Italian restaurant");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, "Japan restaurant");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2, "French restaurant");
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID + 3, "Mexican restaurant");

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdatedRestaurant() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        return updated;
    }

    public static List<Restaurant> getAllSorted() {
        return List.of(
                RESTAURANT_3,
                RESTAURANT_1,
                RESTAURANT_2,
                RESTAURANT_4
        );
    }

    public static Restaurant getVoted() {
        Restaurant restaurantVote = new Restaurant(RESTAURANT_3);
        restaurantVote.setVotes(List.of(VOTE_5, VOTE_4, VOTE_3));
        return restaurantVote;
    }
}