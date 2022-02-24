package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.service.UserService;
import ru.javawebinar.restaurant_voting_system.to.UserTo;
import ru.javawebinar.restaurant_voting_system.util.ToUtil;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTos;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public List<UserTo> getAll() {
        log.info("getAll users");
        return getUserTos(service.getAll());
    }

    public UserTo get(int id) {
        log.info("get user by id={}", id);
        return getUserTo(service.get(id));
    }

    public User create(UserTo userTo) {
        log.info("create user {}", userTo);
        checkNew(userTo);
        return service.create(ToUtil.createNewFromUserTo(userTo));
    }

    public void delete(int id) {
        log.info("delete user with id={}", id);
        service.delete(id);
    }

    public void update(UserTo userTo, int id) {
        log.info("update user {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public UserTo getByMail(String email) {
        log.info("getByEmail={}", email);
        return getUserTo(service.getByEmail(email));
    }
}