package ru.javawebinar.restaurant_voting_system.repository;

import ru.javawebinar.restaurant_voting_system.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to userId
    Vote save(Vote vote, int userId);

    // false if vote do not belong to userId
    boolean delete(int id, int userId);

    // null if vote do not belong to userId
    Vote get(int id, int userId);

    // null if not found
    Vote get(int id);

    // null if not found
    Vote getForToday(int authUserId, LocalDate date);

    // ORDERED bookingDate desc
    List<Vote> getAll();

    // ORDERED bookingDate desc
    List<Vote> getAllByUserId(int userId);

    // ORDERED bookingDate desc
    List<Vote> getBetweenPeriod(LocalDate startDate, LocalDate endDate, int userId);

    default Vote getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }

    default Vote getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}