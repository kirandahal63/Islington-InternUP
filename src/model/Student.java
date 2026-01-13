/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lenovo
 */
public class Student {
    private String studentId;
    private String studentName;
    private String email;
    private String password;

    public Student(String studentName, String password) {
        this.studentName = studentName;
        this.email = studentId+"@islingtoncollege.edu.np";
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public static String isStudentUser(String studentID,String name, String password){
        if (studentID == null ||studentID.isEmpty()){
            return "Please enter your college ID!!";   
        }
        else if (name == null|| name.isEmpty() ){
            return "Please enter your name!!";     
        } 
        else if (!name.contains(" ")){
            return "Please enter your full name!!";            
        }
        else if(password == null || password.isEmpty()){
            return "Please set a password!!"; 
        }
        else if (password.length() < 5) {
            return "Weak Password! \nPassword must be more than 5 characters long!!";
        }
        return null;
    }

}
