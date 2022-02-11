package ru.javawebinar.restaurant_voting_system.data;

import ru.javawebinar.restaurant_voting_system.MatcherFactory;
import ru.javawebinar.restaurant_voting_system.model.Role;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "roles", "votes");

    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserTo.class, "registered", "roles", "votes");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 42;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}