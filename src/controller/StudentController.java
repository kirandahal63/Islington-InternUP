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
    public static String currentStudentId = null; 
    
    static {
        studentMap.put("np01", new Student("np01","Kiran Dahal", "aaaaa"));
        studentMap.put("np02", new Student("np02","Nitya Yadav", "pass124"));
        studentMap.put("np03", new Student("np03","Anwesha Acharya","pass125"));
        studentMap.put("np04", new Student("np04","Alwin Maharjan", "pass126"));
        studentMap.put("np05", new Student("np05","Aditya", "pass123"));
    }
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
        
        Student newStudent = new Student(studentId,name, password);
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
