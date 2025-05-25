/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.Employee;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author lelin
 */
public class EmployeeRepository {
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY employee_id ASC";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("sex"),
                        rs.getString("position"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("hire_date"),
                        rs.getDouble("salary"),
                        rs.getTimestamp("created_at")
                );
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Employee> getAllEmployeesLikeName(String name) {
    List<Employee> list = new ArrayList<>();
    String sql = "SELECT * FROM employees WHERE full_name LIKE ?";

    try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
        stmt.setString(1, "%" + name + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("employee_id"),
                    rs.getString("full_name"),
                    rs.getString("sex"),
                    rs.getString("position"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("hire_date"),
                    rs.getDouble("salary"),
                    rs.getTimestamp("created_at")
                );
                list.add(emp);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    public Employee getAllEmployeesById(int id) {

    String sql = "SELECT * FROM employees WHERE employee_id = ?";

    try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Employee(
                    rs.getInt("employee_id"),
                    rs.getString("full_name"),
                    rs.getString("sex"),
                    rs.getString("position"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("hire_date"),
                    rs.getDouble("salary"),
                    rs.getTimestamp("created_at")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


    public boolean insertEmployee(Employee emp) {
        String sql = "INSERT INTO employees (full_name, sex, position, phone, email, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getSex());
            stmt.setString(3, emp.getPosition());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getEmail());
            stmt.setDate(6, emp.getHireDate());
            stmt.setDouble(7, emp.getSalary());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET full_name=?, sex=?, position=?, phone=?, email=?, hire_date=?, salary=? WHERE employee_id=?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getSex());
            stmt.setString(3, emp.getPosition());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getEmail());
            stmt.setDate(6, emp.getHireDate());
            stmt.setDouble(7, emp.getSalary());
            stmt.setInt(8, emp.getEmployeeId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (PreparedStatement stmt = MySQLConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void importEmployeesFromTable(JTable tbl) {
    TableModel model = tbl.getModel();
    int rowCount = model.getRowCount();

    for (int i = 0; i < rowCount; i++) {
        try {
            String fullName = model.getValueAt(i, 1).toString();
            String sex = model.getValueAt(i, 2).toString();
            String position = model.getValueAt(i, 3).toString();
            String phone = model.getValueAt(i, 4).toString();
            String email = model.getValueAt(i, 5).toString();
            Date hireDate = Date.valueOf(model.getValueAt(i, 6).toString()); // yyyy-MM-dd
            double salary = Double.parseDouble(model.getValueAt(i, 7).toString().replace(",", ""));

            Employee emp = new Employee();
            emp.setFullName(fullName);
            emp.setSex(sex);
            emp.setPosition(position);
            emp.setPhone(phone);
            emp.setEmail(email);
            emp.setHireDate(hireDate);
            emp.setSalary(salary);

            insertEmployee(emp); // Gọi hàm thêm dữ liệu
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Lỗi khi thêm dòng " + (i + 1));
        }
    }
}
}
