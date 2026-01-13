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
    public static HashMap<String, Student> studentMap = new HashMap<>();
    private static String currentStudentId = null; 

    public static String registerNewStudent(String studentId, String name, String password) {
        
        String validation = Student.isStudentUser(studentId, name, password);
        if (validation != null) {
            return validation;
        }
        
        if (studentMap.containsKey(studentId)) {
            return "Student with this College ID already exists!!";
        }        
        
        for (Student s : studentMap.values()) {
            if (s.getPassword().equals(password)) {
                return "Please enter a unique password!!";
            }
        }
        
        Student newStudent = new Student(studentId, name, password);
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
