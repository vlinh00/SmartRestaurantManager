package DAL;

import DTO.Order;
import DTO.OrderItem;
import java.sql.*;
import java.util.*;

public class OrderDAL {
    public List<Order> selectByUser(int userId) throws SQLException {
        String sql = "SELECT order_id, status, total_amount FROM orders WHERE user_id = ?";
        List<Order> list = new ArrayList<>();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("order_id"),
                    rs.getString("status"),
                    rs.getBigDecimal("total_amount")
                ));
            }
        }
        return list;
    }

    public List<OrderItem> selectItems(int orderId) throws SQLException {
        String sql = """
            SELECT od.quantity, p.name
            FROM order_details od
            JOIN products p ON od.product_id = p.product_id
            WHERE od.order_id = ?
        """;
        List<OrderItem> list = new ArrayList<>();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new OrderItem(
                    rs.getString("name"),
                    rs.getInt("quantity")
                ));
            }
        }
        return list;
    }
}
