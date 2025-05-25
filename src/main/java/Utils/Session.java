/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DTO.User;

/**
 *
 * @author lelin
 */
public class Session {
    public static int userId;
    public static String username;
    public static String role;
    public static String fullName;
    public static String phone;
    public static String email;
    public static Integer employeeId;
    
    public static void setPara(User user) {
        userId = user.getUserId();
        username = user.getUsername();
        role = user.getRole();
        fullName = user.getFullName();
        phone = user.getPhone();
        email = user.getEmail();
        employeeId = user.getEmployee_id();
    }
    
    public static void clear() {
        userId = 0;
        username = null;
        role = null;
        fullName = null;
        phone = null;
        email = null;
        employeeId = null;
    }
}
