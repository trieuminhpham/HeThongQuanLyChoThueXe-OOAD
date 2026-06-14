package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /*
     * Sửa USER và PASSWORD theo SQL Server của máy bạn.
     * Cần add file mssql-jdbc-xx.x.x.jre11.jar hoặc jre17.jar vào thư mục lib.
     */
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
            + "databaseName=HeThongQuanLyChoThueXe;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
