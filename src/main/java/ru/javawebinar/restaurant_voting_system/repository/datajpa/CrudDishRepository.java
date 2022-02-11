package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1")
    int delete(int id);

    @EntityGraph(attributePaths = {"restaurant"})
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.restaurant.id=?1 and d.date=?2")
    int deleteMenu(int restaurantId, LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 AND d.date=?2 ORDER BY d.date DESC")
    List<Dish> getDish(int restaurantId, LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC")
    List<Dish> getDishByRestaurantIdBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId  ORDER BY d.date DESC")
    List<Dish> getAllDishByRestaurantId(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC")
    List<Dish> getAllDishBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @EntityGraph(attributePaths = {"restaurant"})
    @Query("SELECT d FROM Dish d WHERE d.id = ?1 and d.restaurant.id = ?2")
    Dish getWithRestaurant(int id, int restaurantId);
}