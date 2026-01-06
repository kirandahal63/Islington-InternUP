/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lenovo
 */
public class Admin {
    
    private String username;
    private String password;

    public Admin(String username, String password) {
        
        this.username = username;
        this.password = password;
    }
   
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String isAdminUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) 
        {
            return "Please enter both username and password";
        }
        if (!this.username.equals(username)) {
            return "Username is incorrect.";
        }
        
        if (!this.password.equals(password)) {
            return "Password is incorrect.";
        }

        return null;
    }
    
}
