/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.DBConfig;
import Utils.DBConfigLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lelin
 */
public class MySQLConnection {

    // Phương thức tĩnh để lấy kết nối
    public static Connection getConnection() {
        try {
            DBConfig config = DBConfigLoader.loadConfig();
            if (config != null) {
                return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
