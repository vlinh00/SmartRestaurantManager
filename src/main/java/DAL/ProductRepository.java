/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.Product;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author lelin
 */
public class ProductRepository {

    public List<Product> getAllData() {
        List<Product> lstProduct = new ArrayList<>();

        String sql = "SELECT * FROM products";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("unit"),
                        rs.getInt("category_id"),
                        rs.getString("image_url"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("updated_at")
                );
                lstProduct.add(product);
            }
            return lstProduct;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Product> getListProductActive() {
        List<Product> lstProduct = new ArrayList<>();

        String sql = "SELECT * FROM products Where is_active = true";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("unit"),
                        rs.getInt("category_id"),
                        rs.getString("image_url"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("updated_at")
                );
                lstProduct.add(product);
            }
            return lstProduct;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Product> getListProductActiveByType(int type) {
        List<Product> lstProduct = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_active = TRUE AND category_id = ?";

        try (
                PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, type);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity"),
                            rs.getString("unit"),
                            rs.getInt("category_id"),
                            rs.getString("image_url"),
                            rs.getBoolean("is_active"),
                            rs.getTimestamp("updated_at")
                    );
                    lstProduct.add(product);
                }
                return lstProduct;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
        }
        return null;
    }

    public boolean insertData(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity, unit, category_id, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setString(5, product.getUnit());
            stmt.setInt(6, product.getCategoryId());
            stmt.setString(7, product.getImageUrl());
            stmt.setBoolean(8, product.isActive());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean updateDataById(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, stock_quantity=?, unit=?, category_id=?, image_url=?, is_active=? WHERE product_id=?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setString(5, product.getUnit());
            stmt.setInt(6, product.getCategoryId());
            stmt.setString(7, product.getImageUrl());
            stmt.setBoolean(8, product.isActive());
            stmt.setInt(9, product.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {

        }
        return false;
    }
    
    public boolean updateQtyById(int id, int qty) {
        String sql = "UPDATE products SET stock_quantity = (stock_quantity + ?) WHERE product_id=?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {

        }
        return false;
    }
    
    public void importProductsFromTable(JTable tbl) {
    int insertedCount = 0;

    for (int i = 0; i < tbl.getRowCount(); i++) {
        try {
            Product product = new Product(
                0, // product_id tự động tăng
                tbl.getValueAt(i, 0).toString(),                       // name
                tbl.getValueAt(i, 1).toString(),                       // description
                Double.parseDouble(tbl.getValueAt(i, 2).toString()),  // price
                Integer.parseInt(tbl.getValueAt(i, 3).toString()),    // stock_quantity
                tbl.getValueAt(i, 4).toString(),                       // unit
                Integer.parseInt(tbl.getValueAt(i, 5).toString()),    // category_id
                tbl.getValueAt(i, 6).toString(),                       // image_url
                Boolean.parseBoolean(tbl.getValueAt(i, 7).toString()),// is_active
                null // updated_at để null, CSDL xử lý nếu cần
            );

            if (insertData(product)) {
                insertedCount++;
            }
        } catch (Exception e) {
            System.err.println("Lỗi dòng " + (i + 1) + ": " + e.getMessage());
            // Có thể log ra file hoặc show ra bảng log nếu cần
        }
    }

    JOptionPane.showMessageDialog(null, "Đã nhập thành công " + insertedCount + " sản phẩm vào CSDL.");
}
    
     public boolean deleteData(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     
     public List<Product> getAllDataByName(String name) {
        List<Product> lstProduct = new ArrayList<>();

        String sql = "SELECT * FROM products Where name like ?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            stmt.setString(1, "%" + name + "%");
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getString("unit"),
                        rs.getInt("category_id"),
                        rs.getString("image_url"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("updated_at")
                );
                lstProduct.add(product);
            }
            return lstProduct;
        } catch (SQLException e) {

        }
        return null;
    }

}
