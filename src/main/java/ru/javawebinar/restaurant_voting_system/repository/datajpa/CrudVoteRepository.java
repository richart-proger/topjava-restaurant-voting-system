package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=?1 AND v.user.id=?2")
    int delete(int id, int userId);

    @EntityGraph(attributePaths = {"restaurant", "user", "user.roles"})
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.bookingDate DESC")
    List<Vote> getAllByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @EntityGraph(attributePaths = {"user", "restaurant"})
    @Query("SELECT v FROM Vote v ORDER BY v.bookingDate DESC")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.bookingDate=:date")
    Vote getForToday(@Param("userId") int userId, @Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.bookingDate >= :startDate AND v.bookingDate < :endDate ORDER BY v.bookingDate DESC")
    List<Vote> getByUserIdBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    @Query("SELECT v FROM Vote v WHERE v.id = ?1 and v.user.id = ?2")
    Vote getWithUser(int id, int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"})
    @Query("SELECT v FROM Vote v WHERE v.id = :userId and v.restaurant.id = :restaurantId")
    Vote getWithRestaurant(@Param("userId") int userId, @Param("restaurantId") int restaurantId);
}