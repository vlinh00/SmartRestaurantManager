/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.CustomerRepository;
import DTO.Customer;
import java.util.List;

/**
 *
 * @author lelin
 */
public class CustomerService {
    private CustomerRepository customerRepo = new CustomerRepository();

    public boolean addCustomer(Customer customer) {
        return customerRepo.insertCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerRepo.getCustomerById(id);
    }
    
    public List<Customer> getCustomerByName(String name) {
        return customerRepo.getCustomerLikeName(name);
    }
    
    public boolean updateCustomer(Customer customer){
        return customerRepo.updateCustomer(customer);
    }
    
    public boolean deleteCustomer(int id){
        return  customerRepo.deleteCustomer(id);
    }
}