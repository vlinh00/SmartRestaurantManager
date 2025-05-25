/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;
import DTO.User;
import DAL.UserRepository;
import java.util.List;
/**
 *
 * @author lelin
 */
public class UserService {
    private UserRepository userRepository = new UserRepository();
    public User login(String user, String pass)
    {
      return userRepository.getUserByUsernameAndPassword(user, pass);
    }
    
    public User getUser(int userId)
    {
      return userRepository.getUserById(userId);
    }
    
    public boolean changePassword(int id, String newPass)
    {
        return userRepository.updatePassword(id, newPass);
    }
    public List<User> getAllUsers()
    {
        return userRepository.getAllData();
    }
    public User getUserByEmpId(int empId){
        return userRepository.getUserByEmpId(empId);
    }
}
