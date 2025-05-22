
package DAL;

/**
 *
 * @author tienn
 */

import DTO.Customer;
import Utils.DBConfigLoader;
import DAL.MySQLConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    // map ResultSet ➜ Customer
    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection c = MySQLConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean insert(Customer c) {
        String sql = "INSERT INTO customers(full_name,phone,email,address) VALUES(?,?,?,?)";
        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getFullName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Customer c) {
        String sql = "UPDATE customers SET full_name=?, phone=?, email=?, address=? WHERE customer_id=?";
        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getFullName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getAddress());
            ps.setInt   (5, c.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try (Connection con = MySQLConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM customers WHERE customer_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
