/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.EmployeeRepository;
import DTO.Employee;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author lelin
 */
public class EmployeeService {
    private EmployeeRepository repo = new EmployeeRepository();

    public List<Employee> getAllEmployees() {
        return repo.getAllEmployees();
    }
    
    public List<Employee> getAllEmployeeByName(String name) {
        return repo.getAllEmployeesLikeName(name);
    }
    
    public Employee getAllEmployeeById (int id) {
        return repo.getAllEmployeesById(id);
    }

    public boolean addEmployee(Employee emp) {
        return repo.insertEmployee(emp);
    }

    public boolean updateEmployee(Employee emp) {
        return repo.updateEmployee(emp);
    }

    public boolean deleteEmployee(int id) {
        return repo.deleteEmployee(id);
    }
    
    public void importFromTable(JTable tbl){
        repo.importEmployeesFromTable(tbl);
    }
}
