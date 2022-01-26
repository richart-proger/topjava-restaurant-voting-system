package ru.javawebinar.restaurant_voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.util.Collection;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.ADMIN_ID;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.USER_ID;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void create() {
        Vote created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, ADMIN_ID), newVote);

        Collection<Vote> allVotes = service.getAllByUserId(ADMIN_ID);
        VOTE_MATCHER.assertMatch(allVotes, VOTE_2, created);
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
        VOTE_MATCHER.assertMatch(allVotes, VOTE_2, VOTE_1, VOTE_3);
    }

    @Test
    public void getAllByUserId() {
        Collection<Vote> allVotes = service.getAllByUserId(USER_ID);
        VOTE_MATCHER.assertMatch(allVotes, VOTE_1, VOTE_3);
    }

    @Test
    public void update() {
        Vote updated = getUpdated();
        service.update(updated, USER_ID);

        updated = getUpdated();
        VOTE_MATCHER.assertMatch(service.get(VOTE_ID, USER_ID), updated);

        Collection<Vote> allVotes = service.getAllByUserId(USER_ID);
        VOTE_MATCHER.assertMatch(allVotes, updated, VOTE_3);
    }
}