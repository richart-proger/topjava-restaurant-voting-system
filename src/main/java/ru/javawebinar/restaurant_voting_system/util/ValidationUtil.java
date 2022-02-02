package ru.javawebinar.restaurant_voting_system.util;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.javawebinar.restaurant_voting_system.model.AbstractBaseEntity;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.util.exception.EmptyMenuException;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;
import ru.javawebinar.restaurant_voting_system.util.exception.TimeExpiredException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static void checkSameDate(LocalDate actualDate, LocalDate expectedDate) {
        if (!actualDate.equals(expectedDate)) {
            throw new DateTimeException("You can't change booking date");
        }
    }

    public static void checkIfTimeHasExpired(LocalTime currentTime) {
        LocalTime timeLimitForVoting = LocalTime.of(11, 0);
        if (currentTime.isAfter(timeLimitForVoting)) {
            throw new TimeExpiredException("It is too late, vote can't be changed");
        }
    }

    public static void checkEmptyMenu(List<Dish> menu) {
        if (menu.isEmpty()) {
            throw new EmptyMenuException("You can't vote the restaurant with empty menu of the day");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}