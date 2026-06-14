package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime parseDateTime(String value) {
        return LocalDateTime.parse(value.trim(), DATE_TIME_FORMAT);
    }

    public static LocalDate parseDate(String value) {
        return LocalDate.parse(value.trim(), DATE_FORMAT);
    }

    public static String formatDateTime(LocalDateTime value) {
        return value == null ? "" : value.format(DATE_TIME_FORMAT);
    }
}
