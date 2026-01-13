/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 *
 * @author lenovo
 */
public class ApplicationController {
    public static LinkedHashMap<String, LinkedHashSet<String>> studentApplications = new LinkedHashMap<>();

    //std apply for internship
    public static boolean applyInternship(String studentId, String internshipId) {

    if (!studentApplications.containsKey(internshipId)) {
        studentApplications.put(internshipId, new LinkedHashSet<>());
    }

    return studentApplications.get(internshipId).add(studentId);
}

    public static HashSet<String> viewApplicants(String internshipId) {

        if (studentApplications.containsKey(internshipId)) {
            return studentApplications.get(internshipId);
        }
        return new HashSet<>();
    }

    public static LinkedHashMap<String, LinkedHashSet<String>> viewAllApplications() {
        return studentApplications;
    } 
    
    public static int totalApplicationCount() {
        int total = 0;
        for (LinkedHashSet<String> applicants : studentApplications.values()) {
            total += applicants.size();
        }
        return total;
    }
    
}
