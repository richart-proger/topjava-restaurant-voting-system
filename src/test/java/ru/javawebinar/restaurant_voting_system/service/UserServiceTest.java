package ru.javawebinar.restaurant_voting_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.restaurant_voting_system.data.UserTestData;
import ru.javawebinar.restaurant_voting_system.model.Role;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User created = service.create(getNew());
        Integer newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UserTestData.NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.USER);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    public void getAll() {
        List<User> allUsers = service.getAll();
        USER_MATCHER.assertMatch(allUsers, ADMIN, USER);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        updated = getUpdated();
        USER_MATCHER.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getWithVotes() {
        User user = service.getWithVotes(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        VOTE_MATCHER.assertMatch(user.getVotes(), VOTE_5, VOTE_2, VOTE_1);
    }

    @Test
    public void getWithVotesNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithVotes(1));
    }
}