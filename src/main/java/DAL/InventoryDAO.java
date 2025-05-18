/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Asus VivoBook
 */
import db.DBConnection;
import java.sql.*;

public class InventoryDAO {
    public void updateInventory(int productId, int quantity) throws SQLException {
        String checkSql = "SELECT quantity FROM inventory WHERE product_id = ?";
        String updateSql = "UPDATE inventory SET quantity = quantity + ? WHERE product_id = ?";
        String insertSql = "INSERT INTO inventory(product_id, quantity) VALUES(?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, productId);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, productId);
                    insertStmt.setInt(2, quantity);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}
