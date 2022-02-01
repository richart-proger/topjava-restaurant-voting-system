package ru.javawebinar.restaurant_voting_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.repository.DishRepository;
import ru.javawebinar.restaurant_voting_system.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.DateUtil.atStartOfDayOrMin;
import static ru.javawebinar.restaurant_voting_system.util.DateUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.*;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final DishRepository dishRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, DishRepository dishRepository) {
        this.voteRepository = voteRepository;
        this.dishRepository = dishRepository;
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "Vote must be not null");
        List<Dish> menu = dishRepository.getMenu(vote.getRestaurant().getId(), vote.getBookingDate());
        checkEmptyMenu(menu);
        return voteRepository.save(vote, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.get(id, userId), id);
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public List<Vote> getAllByUserId(int userId) {
        return voteRepository.getAllByUserId(userId);
    }

    public List<Vote> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return voteRepository.getBetweenPeriod(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public void update(Vote vote, int userId, LocalTime currentTime) {
        Assert.notNull(vote, "Vote must be not null");
        Assert.notNull(currentTime, "LocalTime must be not null");
        checkSameDate(vote.getBookingDate(), get(vote.getId(), userId).getBookingDate());
        checkIfTimeHasExpired(currentTime);
        checkNotFoundWithId(voteRepository.save(vote, userId), vote.getId());
    }

    public Vote getWithUser(int id, int userId) {
        return checkNotFoundWithId(voteRepository.getWithUser(id, userId), id);
    }

    public Vote getWithRestaurant(int id, int restaurantId) {
        return checkNotFoundWithId(voteRepository.getWithRestaurant(id, restaurantId), id);
    }
}