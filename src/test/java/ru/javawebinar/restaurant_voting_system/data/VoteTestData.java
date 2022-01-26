package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Vote;

import java.time.LocalDate;

import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.admin;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.user;
import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurant", "user");

    public static final int VOTE_ID = START_SEQ + 11;
    public static final int NOT_FOUND = 42;

    public static final Vote vote_1 = new Vote(VOTE_ID, user, restaurant_1, LocalDate.now().plusDays(1));
    public static final Vote vote_2 = new Vote(VOTE_ID + 1, admin, restaurant_3, LocalDate.now().plusDays(2));
    public static final Vote vote_3 = new Vote(VOTE_ID + 2, user, restaurant_1, LocalDate.now().minusDays(3));

    public static Vote getNew() {
        return new Vote(null, null, restaurant_2, LocalDate.now());
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote_1);
        updated.setRestaurant(restaurant_2);
        updated.setBookingDate(LocalDate.now());
        return updated;
    }
}