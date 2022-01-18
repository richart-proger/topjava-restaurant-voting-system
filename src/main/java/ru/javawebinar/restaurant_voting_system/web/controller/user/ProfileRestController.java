package ru.javawebinar.restaurant_voting_system.web.controller.user;

import ru.javawebinar.restaurant_voting_system.model.User;

import static ru.javawebinar.restaurant_voting_system.web.SecurityUtil.authUserId;

public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}