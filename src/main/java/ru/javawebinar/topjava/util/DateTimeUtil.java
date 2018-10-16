package ru.javawebinar.topjava.util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetween(T val, T startVal, T endVal) {
        return val.compareTo(startVal) >= 0 && val.compareTo(endVal) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate getDateFromParameter(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return ((parameter == null) || parameter.isEmpty()) ? null : LocalDate.parse(parameter);
    }

    public static LocalTime getTimeFromParameter(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return ((parameter == null) || parameter.isEmpty()) ? null : LocalTime.parse(parameter);
    }
}
