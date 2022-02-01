package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int delete(int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.restaurantMenu WHERE r.id = ?1")
    Restaurant getWithDishes(int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.votes WHERE r.id = ?1")
    Restaurant getWithVotes(int id);
}