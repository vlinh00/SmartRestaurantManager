/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.Promotion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lelin
 */
public class PromotionRepository {

    public boolean insert(Promotion promo) {
        String sql = "INSERT INTO promotions (name, description, discount_percent, min_order_amount, start_date, end_date, active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, promo.getName());
            stmt.setString(2, promo.getDescription());
            stmt.setDouble(3, promo.getDiscountPercent());
            stmt.setDouble(4, promo.getMinOrderAmount());
            stmt.setDate(5, promo.getStartDate());
            stmt.setDate(6, promo.getEndDate());
            stmt.setBoolean(7, promo.isActive());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Promotion> getAll() {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM promotions ORDER BY start_date DESC";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Promotion p = new Promotion(
                    rs.getInt("promo_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("discount_percent"),
                    rs.getDouble("min_order_amount"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getBoolean("active")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Promotion getById(int promoId) {
        String sql = "SELECT * FROM promotions WHERE promo_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Promotion(
                    rs.getInt("promo_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("discount_percent"),
                    rs.getDouble("min_order_amount"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getBoolean("active")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean update(Promotion promo) {
    String sql = "UPDATE promotions SET name = ?, description = ?, discount_percent = ?, min_order_amount = ?, start_date = ?, end_date = ?, active = ? WHERE promo_id = ?";
    
    try (Connection conn = MySQLConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, promo.getName());
        stmt.setString(2, promo.getDescription());
        stmt.setDouble(3, promo.getDiscountPercent());
        stmt.setDouble(4, promo.getMinOrderAmount());
        stmt.setDate(5, promo.getStartDate());
        stmt.setDate(6, promo.getEndDate());
        stmt.setBoolean(7, promo.isActive());
        stmt.setInt(8, promo.getPromoId());

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

}