package util;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {
    public static String formatVND(double money) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(money) + " VNĐ";
    }
}
