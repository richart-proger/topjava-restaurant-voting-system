package ru.javawebinar.restaurant_voting_system.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DateUtil {

    public static final LocalTime TIME_LIMIT_FOR_VOTING = LocalTime.of(11, 0);
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static LocalDate atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE;
    }

    public static List<LocalDate> getDateList(LocalDate startDate, LocalDate endDate) {
        startDate = startDate == null ? LocalDate.now() : atStartOfDayOrMin(startDate);
        endDate = endDate == null ? LocalDate.now() : atStartOfNextDayOrMax(endDate);

        if (startDate.isAfter(endDate)) {
            throw new DateTimeException("startDate must be before endDate");
        }
        //  https://ru.stackoverflow.com/questions/827427/%D0%9A%D0%B0%D0%BA-%D1%81%D0%B3%D0%B5%D0%BD%D0%B5%D1%80%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-stream-%D0%B4%D0%B0%D1%82
        return startDate.equals(endDate) ? List.of(startDate) : startDate.datesUntil(endDate.plusDays(1)).toList();
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasLength(str) ? LocalDate.parse(str) : null;
    }
}