package ru.javawebinar.restaurant_voting_system.repository;

import ru.javawebinar.restaurant_voting_system.model.Vote;

import java.util.Collection;

public interface VoteRepository {
    // null if updated vote do not belong to userId
    Vote save(Vote vote, int userId);

    // false if vote do not belong to userId
    boolean delete(int id, int userId);

    // null if vote do not belong to userId
    Vote get(int id, int userId);

    // ORDERED bookingDate desc
    Collection<Vote> getAll();

    // ORDERED bookingDate desc
    Collection<Vote> getAllByUserId(int userId);
}