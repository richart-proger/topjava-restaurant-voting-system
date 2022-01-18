package ru.javawebinar.restaurant_voting_system.repository;

import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    // null if not found, when updated
    Restaurant save(Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // false if not found
    Restaurant get(int id);

    List<Restaurant> getAll();
}
