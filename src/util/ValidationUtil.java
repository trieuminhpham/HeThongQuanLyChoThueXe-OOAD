package util;

import java.time.LocalDateTime;

public class ValidationUtil {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,11}");
    }

    public static boolean isValidCCCD(String cccd) {
        return cccd != null && cccd.matches("\\d{12}");
    }

    public static boolean isValidBookingTime(LocalDateTime from, LocalDateTime to) {
        return from != null && to != null
                && !from.isBefore(LocalDateTime.now().minusMinutes(1))
                && to.isAfter(from);
    }

    public static boolean isValidEmail(String email) {
        return isEmpty(email) || email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }
}
