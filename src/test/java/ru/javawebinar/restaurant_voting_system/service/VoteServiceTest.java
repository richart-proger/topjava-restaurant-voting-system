package ru.javawebinar.restaurant_voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.util.exception.EmptyMenuException;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;
import ru.javawebinar.restaurant_voting_system.util.exception.TimeExpiredException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.ADMIN_ID;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.USER_ID;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("votes").clear();
    }

    @Test
    public void create() {
        Vote created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, ADMIN_ID), newVote);

        Collection<Vote> allVotes = service.getAllByUserId(ADMIN_ID);
        VOTE_MATCHER.assertMatch(allVotes, created, VOTE_4, VOTE_3);
    }

    @Test
    public void createWithEmptyMenu() {
        assertThrows(EmptyMenuException.class, () -> service.create(getNewWithEmptyMenu(), ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(VOTE_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void get() {
        Vote vote = service.get(VOTE_ID, USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getAll() {
        Collection<Vote> allVotes = service.getAll();
        VOTE_MATCHER.assertMatch(allVotes, VOTE_5, VOTE_4, VOTE_3, VOTE_2, VOTE_1);
    }

    @Test
    public void getAllByUserId() {
        Collection<Vote> allVotes = service.getAllByUserId(USER_ID);
        VOTE_MATCHER.assertMatch(allVotes, VOTE_5, VOTE_2, VOTE_1);
    }

    @Test
    public void update() {
        Vote updated = getUpdated();

        LocalTime currentTime = LocalTime.of(9, 0);
        service.update(updated, USER_ID, currentTime);

        updated = getUpdated();
        VOTE_MATCHER.assertMatch(service.get(VOTE_5.getId(), USER_ID), updated);

        Collection<Vote> allVotes = service.getAllByUserId(USER_ID);
        VOTE_MATCHER.assertMatch(allVotes, updated, VOTE_2, VOTE_1);
    }

    @Test
    public void updateNotOwn() {
        LocalTime currentTime = LocalTime.of(9, 0);
        assertThrows(NotFoundException.class, () -> service.update(VOTE_2, ADMIN_ID, currentTime));
    }

    @Test
    public void updateTimeExpired() {
        LocalTime currentTime = LocalTime.of(11, 1);
        assertThrows(TimeExpiredException.class, () -> service.update(VOTE_1, USER_ID, currentTime));
    }

    @Test
    public void updateWrongDate() {
        Vote updated = getUpdated();
        updated.setBookingDate(LocalDate.now().minusDays(1));
        LocalTime currentTime = LocalTime.of(10, 0);
        assertThrows(DateTimeException.class, () -> service.update(updated, USER_ID, currentTime));
    }

    @Test
    void getBetweenInclusive() {
        VOTE_MATCHER.assertMatch(
                service.getBetweenInclusive(LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), USER_ID),
                VOTE_2, VOTE_1
        );
    }

    @Test
    void getBetweenWithNullDates() {
        VOTE_MATCHER.assertMatch(service.getBetweenInclusive(null, null, ADMIN_ID), VOTE_4, VOTE_3);
    }
}