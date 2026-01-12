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
    private String email;
    private String password;

    public Student(String studentId,String email, String password) {

        this.studentId = studentId;
        this.email = email;
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public static String isStudentUser(String studentID,String email, String password){
        if (studentID == null ||studentID.isEmpty()){
            return "Please enter your college ID!!";   
        }
        else if (email == null|| email.isEmpty() ){
            return "Please enter your email!!";     
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
