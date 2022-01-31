package ru.javawebinar.restaurant_voting_system.repository.datajpa;

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
    @Query("DELETE FROM Dish d WHERE d.id=?1 AND d.restaurant.id=?2")
    int delete(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 AND d.date=?2 ORDER BY d.date DESC")
    List<Dish> getMenu(int restaurantId, LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC")
    List<Dish> getMenuByRestaurantIdBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC")
    List<Dish> getAllMenusBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}