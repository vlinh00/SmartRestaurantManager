package DAL;

import DTO.CartItem;
import java.sql.*;
import java.util.*;

public class CartDAL {
    public int getOrCreateOpenCart(int userId) throws SQLException {
        // 1. Tìm giỏ hàng open hiện có
        String selectSql = "SELECT cart_id FROM carts WHERE user_id = ? AND status = 'open' LIMIT 1";
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(selectSql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        }

        // 2. Nếu chưa có, tạo mới
        String insertSql = "INSERT INTO carts (user_id, status) VALUES (?, 'open')";
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, userId);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("Không thể tạo hoặc lấy cart!");
    }

    public void addItem(int cartId,int productId,int qty,java.math.BigDecimal price) throws SQLException {
        // Thêm sản phẩm vào giỏ hàng, kiểm tra nếu đã có thì cộng dồn
        String selectSql = "SELECT cart_item_id, quantity FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(selectSql)) {
            st.setInt(1, cartId);
            st.setInt(2, productId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                // Nếu đã có thì update số lượng
                int cartItemId = rs.getInt("cart_item_id");
                int oldQty = rs.getInt("quantity");
                String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_item_id = ?";
                try (PreparedStatement up = c.prepareStatement(updateSql)) {
                    up.setInt(1, qty);
                    up.setInt(2, cartItemId);
                    up.executeUpdate();
                }
            } else {
                // Chưa có thì insert mới
                String insertSql = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ins = c.prepareStatement(insertSql)) {
                    ins.setInt(1, cartId);
                    ins.setInt(2, productId);
                    ins.setInt(3, qty);
                    ins.setBigDecimal(4, price);
                    ins.executeUpdate();
                }
            }
        }
    }

    public List<CartItem> getItems(int cartId) throws SQLException {
        String sql = """
            SELECT ci.cart_item_id, ci.product_id, ci.quantity, ci.price, p.name
            FROM cart_items ci
            JOIN products p ON ci.product_id = p.product_id
            WHERE ci.cart_id = ?
        """;
        List<CartItem> list = new ArrayList<>();
        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, cartId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(CartItem.fromResultSet(rs));
            }
        }
        return list;
    }
}
