/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.*;
import dao.UserDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author namdng09
 */
public class Validation {
    UserDAO dao = new UserDAO();

    public boolean isValidUsername(String username) {
        boolean flag = false;
        // username do not start-end with the hyphen and contain multiple hyphen
        // Only allows alphanumberic characters and single hyphen
        // Length of username should be min = 4, max = 20
        String regex = "^(?!-)(?!.*--)[a-zA-Z0-9-]{4,20}(?<!-)$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(username);
        
        if (!matcher.matches()) {
            return flag;
        }
        // check duplicate username
        try {
            ResultSet rs = dao.getUserByUsername(username);
            String nameOfUser = rs.getString("username");
            if (nameOfUser != null) {
                // username already exists
                return flag;
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("ERROR: " + e.getMessage());
            return flag;
        }
        flag = true;
        return flag;
    }
    public boolean isValidEmail(String email) {
        boolean flag = false;
        // The top level domain must be at least 2 characters long.
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) {
            return flag;
        }
        // check duplicate username
        try {
            ResultSet rs = dao.getUserByEmail(email);
            String nameOfUser = rs.getString("username");
            if (nameOfUser != null) {
                return flag;
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("ERROR: " + e.getMessage());
        }
        flag = true;
        return flag;
    }


//    public boolean isValidPassword(String password) {
//        // meet up the pattern
//    }
}
