/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;
import DTO.User;
import DAL.UserRepository;
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
}
