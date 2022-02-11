package ru.javawebinar.restaurant_voting_system.web;

import ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity;

public class SecurityUtil {

    private SecurityUtil() {
    }

    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

    public static void setId(int id) {
        SecurityUtil.id = id;
    }
}