package ru.javawebinar.restaurant_voting_system.repository;

import ru.javawebinar.restaurant_voting_system.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    // null if updated dish do not belong to restaurantId
    Dish save(Dish dish);

    // false if dish do not belong to restaurantId
    boolean delete(int id);

    // null if dish do not belong to restaurantId
    Dish get(int id);

    // ORDERED date desc
    List<Dish> getAll();

    // ORDERED date desc
    List<Dish> getMenu(int restaurantId, LocalDate date);

    // ORDERED date desc
    List<Dish> getMenuByRestaurantIdBetweenPeriod(LocalDate startDate, LocalDate endDate, int restaurantId);

    // ORDERED date desc
    List<Dish> getAllMenusBetweenPeriod(LocalDate startDate, LocalDate endDate);
}