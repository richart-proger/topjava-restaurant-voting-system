package ru.javawebinar.restaurant_voting_system.repository.datajpa;

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

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.bookingDate DESC")
    List<Vote> getAllByUserId(@Param("userId") int userId);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.bookingDate >= :startDate AND v.bookingDate < :endDate ORDER BY v.bookingDate DESC")
    List<Vote> getByUserIdBetweenPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);
}