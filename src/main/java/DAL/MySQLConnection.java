package DAL;

import Utils.DBConfigLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection getConnection() {
        try {
            // Đọc config từ file properties (dùng DBConfigLoader)
            String driver = DBConfigLoader.get("driver");
            String url    = DBConfigLoader.get("url");
            String user   = DBConfigLoader.get("username");
            String pass   = DBConfigLoader.get("password");
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Không thể kết nối CSDL", e);
        }
    }
}
