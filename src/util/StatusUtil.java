package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StatusUtil {
    public static final String HD_CHO_DUYET = "ChoDuyet";
    public static final String HD_GIU_CHO = "GiuCho";
    public static final String HD_DA_DAT_COC = "DaDatCoc";
    public static final String HD_DANG_THUE = "DangThue";
    public static final String HD_CHO_QUYET_TOAN = "ChoQuyetToan";
    public static final String HD_DA_QUYET_TOAN = "DaQuyetToan";
    public static final String HD_DA_HUY = "DaHuy";

    public static final String XE_SAN_SANG = "SanSang";
    public static final String XE_GIU_CHO = "GiuCho";
    public static final String XE_DA_DAT_COC = "DaDatCoc";
    public static final String XE_DANG_THUE = "DangThue";
    public static final String XE_CHO_QUYET_TOAN = "ChoQuyetToan";
    public static final String XE_BAO_TRI = "BaoTri";
    public static final String XE_BAO_DUONG = "BaoDuong";
    public static final String XE_DA_THANH_LY = "DaThanhLy";

    private static final Set<String> XE_KHONG_SAN_SANG = new HashSet<>(Arrays.asList(
            XE_GIU_CHO, XE_DA_DAT_COC, XE_DANG_THUE, XE_CHO_QUYET_TOAN
    ));

    public static boolean isXeAvailable(String status) {
        return XE_SAN_SANG.equals(status);
    }

    public static boolean isXeBookedOrRented(String status) {
        return XE_KHONG_SAN_SANG.contains(status);
    }

    public static String displayHopDong(String status) {
        if (status == null) return "";
        switch (status) {
            case HD_CHO_DUYET: return "Chờ duyệt";
            case HD_GIU_CHO: return "Giữ chỗ";
            case HD_DA_DAT_COC: return "Đã đặt cọc";
            case HD_DANG_THUE: return "Đang thuê";
            case HD_CHO_QUYET_TOAN: return "Chờ quyết toán";
            case HD_DA_QUYET_TOAN: return "Đã quyết toán";
            case HD_DA_HUY: return "Đã hủy";
            default: return status;
        }
    }

    public static String displayXe(String status) {
        if (status == null) return "";
        switch (status) {
            case XE_SAN_SANG: return "Sẵn sàng";
            case XE_GIU_CHO: return "Đã giữ chỗ";
            case XE_DA_DAT_COC: return "Đã đặt cọc";
            case XE_DANG_THUE: return "Đang thuê";
            case XE_CHO_QUYET_TOAN: return "Chờ quyết toán";
            case XE_BAO_TRI: return "Bảo trì";
            case XE_BAO_DUONG: return "Bảo dưỡng";
            case XE_DA_THANH_LY: return "Đã thanh lý";
            default: return status;
        }
    }
}
