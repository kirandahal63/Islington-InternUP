/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.ArrayList;
import java.util.HashMap;
import model.Student;
/**
 *
 * @author lenovo
 */
public class StudentController 
{
    private static HashMap<String, Student> studentMap = new HashMap<>();

    public String registerNewStudent(String studentId, String email, String password) {
        
        if (studentMap.containsKey(studentId)) {
            return "Student with this College ID already exists!!";
        }

        if (password.length() < 5) {
            return "Not a Strong Password! \nPassword must be more than 5 characters long!!";
        }
        for (Student s : studentMap.values()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return "User with this email already exists!!";
            }
            if (s.getPassword().equals(password)) {
                return "Please enter a unique password!!";
            }
        }

        // Create new student and add to map
        Student newStudent = new Student(studentId, email, password);
        studentMap.put(studentId, newStudent);

        return null; // null means registration successful
    }

    public static boolean loginStudent(String studentId, String password) {
        if (!studentMap.containsKey(studentId)) {
            return false;
        }
        Student s = studentMap.get(studentId);
        return s.getPassword().equals(password);
    }    
}
