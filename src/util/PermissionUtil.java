package util;

import config.Session;

public final class PermissionUtil {
    private PermissionUtil() {}

    public static boolean isOwner() {
        return Session.isChuCuaHang();
    }

    public static boolean isSales() {
        return Session.isNhanVienKinhDoanh();
    }

    public static boolean isTechnician() {
        return Session.isNhanVienKyThuat();
    }

    public static boolean canManageBookings() {
        return isOwner() || isSales();
    }

    public static boolean canManageCustomers() {
        return isOwner() || isSales();
    }

    public static boolean canSettleContracts() {
        return isOwner() || isSales();
    }

    public static boolean canHandleVehicleHandover() {
        return isOwner() || isTechnician();
    }

    public static boolean canManageVehicleCatalog() {
        return isOwner();
    }

    public static boolean canUpdateTechnicalStatus() {
        return isOwner() || isTechnician();
    }

    public static boolean canManageEmployees() {
        return isOwner();
    }

    public static boolean canViewReports() {
        return isOwner();
    }

    public static boolean canManagePromotions() {
        return isOwner();
    }
}
