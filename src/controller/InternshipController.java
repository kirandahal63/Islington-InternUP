/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.LinkedList;
import model.Internship;

/**
 *
 * @author lenovo
 */
public class InternshipController {
    public static LinkedList<Internship> internshipList = new LinkedList<>();
    public static LinkedList<Internship> deletedInternshipList = new LinkedList<>();
    
    public static void addDefaultInternship ()
    {
        internshipList = new LinkedList<>();

        // Pre-added internships
        internshipList.add(new Internship(
                "Java Intern",
                "ING Tech",
                "2026-Jan-30",
                15000,
                "On site",
                "3 Months",
                "Work on Java projects with senior developers.",
                "Java, OOP, Experience on Netbeans"
        ));

        internshipList.add(new Internship(
                "Web Intern",
                "Webify",
                "2026-Jan-25",
                12000,
                "Remote",
                "6 Months",
                "Assist in frontend web development tasks.",
                "HTML, CSS,  JS"
        ));
        
        internshipList.add(new Internship(
                "UI/UX Intern",
                "Design Hub Nationals",
                "2026-Feb-2",
                25000,
                "Remote",
                "1 Month",
                "UI research and design.",
                "Figma, Canva, Photoshop, creativity"
        ));
        
        internshipList.add(new Internship(
                "Data Analyst Intern",
                "DataSite",
                "2026-Feb-31",
                35000,
                "International",
                "6 Months",
                "Data analysis and reports.",
                "Excel, SQL basics, Power BI"
        ));
        
        internshipList.add(new Internship(
                "AI Intern",
                "Skill Academy",
                "2026-Jan-20",
                25000,
                "Remote",
                "1 Months",
                "Machine learning models",
                "Python, pyCharm, ML basics"
        ));
        internshipList.add(new Internship(
                "Generative AI Instructor",
                "Danson Solutions",
                "2026-Jan-29",
                20000,
                "Onsite",
                "3 Months",
                "Teach core GenAI concepts in a simple, practical way",
                "Good understanding of Generative AI concepts"
        ));
        
        internshipList.add(new Internship(
                "AI Intern",
                "Skill Academy",
                "2026-Jan-20",
                25000,
                "Remote",
                "1 Months",
                "Machine learning models",
                "Python, pyCharm, ML basics"
        ));
        internshipList.add(new Internship(
                "Generative AI Instructor",
                "Danson Solutions",
                "2026-Jan-29",
                20000,
                "Onsite",
                "3 Months",
                "Teach core GenAI concepts in a simple, practical way",
                "Good understanding of Generative AI concepts"
        ));
    }
    
    public static void addInternship(String title, String company, String deadline,int salary, String type, String duration, String description, String requirement)
    {
        Internship internship = new Internship(title, company, deadline,salary, type, duration, description, requirement);
        internshipList.add(internship);
    }
        
    public static boolean isSameAsOriginal(int index,String title,String company,String deadline,int salary,String type,String duration,String description,String requirement) 
    {
        Internship i = internshipList.get(index);
        return i.getTitle().equalsIgnoreCase(title)&& i.getCompany().equalsIgnoreCase(company) && i.getDeadline().equals(deadline) && i.getSalary() == salary && i.getType().equalsIgnoreCase(type)        && i.getDuration().equalsIgnoreCase(duration)
            && i.getDescription().equalsIgnoreCase(description) && i.getRequirement().equalsIgnoreCase(requirement);
    }
    
    public static boolean isDuplicateForUpdate(int editingIndex,String title,String company,String deadline,int salary,String type,String duration,String description,String requirement) 
    {
        for (int i = 0; i < internshipList.size(); i++) {

            if (i == editingIndex) continue;

            Internship in = internshipList.get(i);

            if (in.getTitle().equalsIgnoreCase(title) && in.getCompany().equalsIgnoreCase(company) && in.getDeadline().equals(deadline) && in.getSalary() == salary
                && in.getType().equalsIgnoreCase(type) && in.getDuration().equalsIgnoreCase(duration) && in.getDescription().equalsIgnoreCase(description) 
                && in.getRequirement().equalsIgnoreCase(requirement)) {

                return true;
            }
        }
        return false;
    }
    
    public static boolean deleteInternship(int index) 
    {
        if (index >= 0 && index < internshipList.size()) {
            Internship removedinternship = internshipList.remove(index); 
            deletedInternshipList.add(removedinternship);  
            return true;
        }
        return false;
    }
    
    public static void updateInternship(int index, String title, String company,String deadline, int salary, String type, String duration,String description, String requirement) 
    {
        Internship i = internshipList.get(index);
        i.setTitle(title);
        i.setCompany(company);
        i.setDeadline(deadline);
        i.setSalary(salary);
        i.setType(type);
        i.setDuration(duration);
        i.setDescription(description);
        i.setRequirement(requirement);
    }
    
}
