package ru.javawebinar.restaurant_voting_system.repository;

import ru.javawebinar.restaurant_voting_system.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {

    // null if not found, when updated
    Dish save(Dish dish);

    // null if not found, when updated
    List<Dish> saveMenu(List<Dish> menu);

    // false if not found
    boolean delete(int id);

    // false if not found
    boolean deleteMenu(int restaurantId, LocalDate date);

    // null if dish do not belong to restaurantId
    Dish getByRestaurantId(int id, int restaurantId);

    // null if not found
    Dish get(int id);

    // ORDERED date desc
    List<Dish> getAll();

    // ORDERED date desc
    List<Dish> getDish(int restaurantId, LocalDate date);

    // ORDERED date desc
    List<Dish> getDishByRestaurantIdBetweenPeriod(LocalDate startDate, LocalDate endDate, int restaurantId);

    // ORDERED date desc
    List<Dish> getAllDishByRestaurantId(int restaurantId);

    // ORDERED date desc
    List<Dish> getAllDishBetweenPeriod(LocalDate startDate, LocalDate endDate);

    default Dish getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}