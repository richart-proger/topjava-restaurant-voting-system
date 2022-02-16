package ru.javawebinar.restaurant_voting_system.util;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import ru.javawebinar.restaurant_voting_system.HasId;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.util.exception.EmptyMenuException;
import ru.javawebinar.restaurant_voting_system.util.exception.InvalidDataFieldException;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;
import ru.javawebinar.restaurant_voting_system.util.exception.TimeExpiredException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.FormatStyle.SHORT;
import static ru.javawebinar.restaurant_voting_system.util.DateUtil.TIME_LIMIT_FOR_VOTING;

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

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static void checkSameDate(LocalDate actualDate, LocalDate expectedDate) {
        if (!actualDate.equals(expectedDate)) {
            throw new DateTimeException("You can't change booking date");
        }
    }

    public static void checkIfTimeHasExpired(LocalTime currentTime) {
        if (currentTime.isAfter(TIME_LIMIT_FOR_VOTING)) {
            throw new TimeExpiredException("You have already voted today. Vote can be change before 11:00. Now it's " + currentTime.format(DateTimeFormatter.ofLocalizedTime(SHORT)) + ". It is too late, your vote can't be changed");
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

    public static void validateBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            String errorFieldsMsg = result.getFieldErrors().stream()
                    .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.joining("<br>"));
            throw new InvalidDataFieldException(errorFieldsMsg);
        }
    }
}