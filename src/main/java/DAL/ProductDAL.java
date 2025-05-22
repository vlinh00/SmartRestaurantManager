package DAL;

import DTO.Product;
import java.sql.*;
import java.util.*;

public class ProductDAL {

    /** lấy mọi sản phẩm đang hoạt động, lọc theo category nếu có */
    public List<Product> getActiveProducts(String category) {
        String sql = """
            SELECT product_id, name, description, price, category, is_active
              FROM products
             WHERE is_active = TRUE
            """ + (category != null ? " AND category = ?" : "");
        List<Product> list = new ArrayList<>();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (category != null) stmt.setString(1, category);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getString("category"),
                        rs.getBoolean("is_active")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** lấy danh sách món trong daily_menu của hôm nay */
    public List<Product> getTodayMenu() {
        String sql = """
            SELECT p.*
            FROM daily_menu dm
            JOIN products p ON dm.product_id = p.product_id
            WHERE dm.menu_date = CURDATE() AND p.is_active = TRUE
        """;
        List<Product> list = new ArrayList<>();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getString("category"),
                    rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}  
