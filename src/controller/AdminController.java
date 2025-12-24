/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Admin;
/**
 *
 * @author lenovo
 */
public class AdminController 
{
    
    private Admin admin;

    public AdminController() 
    {
        admin = new Admin("admin", "admin123");
    }

    public boolean login(String username, String password) 
    {
        return admin.adminValidation(username, password);
    }
    
}
