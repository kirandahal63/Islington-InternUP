/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.HashMap;
import model.Student;
/**
 *
 * @author lenovo
 */
public class StudentController 
{
    private static HashMap<String, Student> studentMap = new HashMap<>();
    private static String currentStudentId = null; 

    public static String registerNewStudent(String studentId, String email, String password) {
        
        String validation = Student.isStudentUser(studentId, email, password);
        if (validation != null) {
            return validation;
        }
        
        if (studentMap.containsKey(studentId)) {
            return "Student with this College ID already exists!!";
        }        
        
        for (Student s : studentMap.values()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return "User with this email already exists!!";
            }
            if (s.getPassword().equals(password)) {
                return "Please enter a unique password!!";
            }
        }
        
        Student newStudent = new Student(studentId, email, password);
        studentMap.put(studentId, newStudent);

        return null; 
    }

    public static Boolean loginStudent(String studentId, String password) {
        
        if (!studentMap.containsKey(studentId)) {
            return false;
        }
        Student s = studentMap.get(studentId);
        if (s.getPassword().equals(password)) {
            currentStudentId = studentId; 
            return true;
        }
        return false;
    }
    
    public static String getCurrentStudentId() {
        return currentStudentId;
    }

    public static void logoutStudent() {
        currentStudentId = null;
    }
    
}
