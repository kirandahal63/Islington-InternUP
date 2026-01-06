/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.ArrayList;
import model.Student;
/**
 *
 * @author lenovo
 */
public class StudentController 
{
    private static ArrayList<Student> studentList = new ArrayList<>();

    public String registerNewStudent(String studentId, String email, String password) 
    {
        for (Student s : studentList) 
        {
            if (s.getStudentId().equalsIgnoreCase(studentId)) 
            {
                return "Student with this ID is already registered";
                
            }
            else if (s.getEmail().equalsIgnoreCase(email)) 
            {
                return "Student with this email is already registered";
            }
            else if (s.getPassword().equals(password) || password.length()<6) 
            {
                return "Please use Strong password";
            }            
        }
        Student newStudent = new Student(studentId, email, password);
        studentList.add(newStudent);
        return null;
    }
    
    public static boolean loginStudent(String email, String password) {

        for (Student s : studentList) {
            if (s.getStudentId().equalsIgnoreCase(email) && s.getPassword().equals(password)) {
                return true; 
            }
        }
        return false; 
    }

    public static ArrayList<Student> getStudentList() {
        return studentList;
    }
    
}
