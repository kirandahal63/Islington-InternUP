/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lenovo
 */
public class Internship {
    private String id;
    private String title;
    private String company;    
    private String deadline;
    private int salary;
    private String type;
    private String duration;
    private String description;
    private String requirement;
    private static int number =1;
    
    //constructor 
    public Internship(String title, String company, String deadline,int salary, String type, String duration, String description, String requirement){
        this.id = "EPD" + number++;
        this.title = title;
        this.company = company;
        this.deadline = deadline;
        this.salary = salary;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.requirement = requirement;
                      
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getCompany() {
        return company;
    }

    public String getDeadline() {
        return deadline;
    }
    
    public  int getSalary(){
        return salary;
    }
    public String getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }
    public String getDescription() {
        return description;
    }
    
    public String getRequirement() {
        return requirement;
    }
    
    public void setTitle(String title) {
    this.title = title;
}

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
}

}
