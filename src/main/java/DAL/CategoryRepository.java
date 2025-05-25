/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lelin
 */
public class CategoryRepository {

    public List<Category> getAllData() {
        List<Category> lstCategorys = new ArrayList<>();

        String sql = "SELECT * FROM categories Order by category_id ASC";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
                lstCategorys.add(category);
            }
            return lstCategorys;
        } catch (SQLException e) {

        }
        return null;
    }

    public Map<Integer, String> getCategoryIdNameMap() {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT category_id, name FROM categories";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("category_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
    
    public boolean insertData(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateData(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE category_id = ?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteData(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Category getCategoryById(int id) {
    String sql = "SELECT * FROM categories WHERE category_id = ?";
    try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Category(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; 
}

}
