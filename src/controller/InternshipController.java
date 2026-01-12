/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.ArrayList;
import java.util.LinkedList;
import model.Internship;

/**
 *
 * @author lenovo
 */
public class InternshipController {
    public static LinkedList<Internship> internshipList = new LinkedList<>();
    static final int STACK_MAX = 50;
    public static Internship[] deletedInternshipStack = new Internship[STACK_MAX];
    public static int top = -1;

    
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
                "2026-Feb-02",
                25000,
                "Remote",
                "1 Month",
                "UI research and design.",
                "Figma, Canva, Photoshop, creativity"
        ));
        
        internshipList.add(new Internship(
                "Data Analyst Intern",
                "DataSite",
                "2026-Feb-28",
                35000,
                "International",
                "6 Months",
                "Data analysis and reports.",
                "Excel, SQL basics, Power BI"
        ));
        
        internshipList.add(new Internship(
                "AI Intern",
                "Skill Academy",
                "2026-Jan-24",
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
                "2026-Feb-16",
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
    
    public static boolean deleteInternship(int index) {
        if (index >= 0 && index < internshipList.size()) {

            Internship removed = internshipList.remove(index);

            // STACK PUSH
            if (top == STACK_MAX - 1) {
                return false; // stack overflow
            }

            top++;
            deletedInternshipStack[top] = removed;

            return true;
        }
        return false;
    }
    
    public static boolean restoreInternship() {

        if (top == -1) {
            return false; // stack underflow
        }

        Internship restored = deletedInternshipStack[top];
        deletedInternshipStack[top] = null;
        top--;

        internshipList.add(restored);
        return true;
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
    
    public static void sortById() {
        int size = internshipList.size();
        for (int step = 0; step < size - 1; step++) {
            int minIndex = step;
            for (int i = step + 1; i < size; i++) {
                if (internshipList.get(i).getId().compareToIgnoreCase(internshipList.get(minIndex).getId()) < 0) {
                    minIndex = i;
                }
            }
            Internship temp = internshipList.get(minIndex);
            internshipList.set(minIndex, internshipList.get(step));
            internshipList.set(step, temp);
        }
    }
    public static void sortByTitle() {
        int size = internshipList.size();
        for (int step = 0; step < size - 1; step++) {
            int minIndex = step;
            for (int i = step + 1; i < size; i++) {
                if (internshipList.get(i).getTitle().compareToIgnoreCase(internshipList.get(minIndex).getTitle()) < 0) {
                    minIndex = i;
                }
            }
            Internship temp = internshipList.get(minIndex);
            internshipList.set(minIndex, internshipList.get(step));
            internshipList.set(step, temp);
        }
    }
    //insertation sort
    public static void sortByCompany() {
        int size = internshipList.size();
        for (int step = 1; step < size; step++) {

            Internship key = internshipList.get(step);
            int i = step- 1;

            while (i >= 0 &&
                   internshipList.get(i).getCompany().compareToIgnoreCase(key.getCompany()) > 0) {

                internshipList.set(i + 1, internshipList.get(i));
                i--;
            }

            internshipList.set(i + 1, key);
        }
    }
    
    public static void sortByType() {
        int size = internshipList.size();
        for (int step = 1; step < size; step++) {

            Internship key = internshipList.get(step);
            int i = step - 1;

            while (i >= 0 && internshipList.get(i).getType().compareToIgnoreCase(key.getType()) > 0) {

                internshipList.set(i + 1, internshipList.get(i));
                i--;
            }

            internshipList.set(i + 1, key);
        }
    }
    public static void sortByDuration() {
        int size = internshipList.size();
        for (int step = 1; step < size; step++) {

            Internship key = internshipList.get(step);
            int i = step - 1;

            while (i >= 0 && internshipList.get(i).getDuration().compareToIgnoreCase(key.getDuration()) < 0) {

                internshipList.set(i + 1, internshipList.get(i));
                i--;
            }

            internshipList.set(i + 1, key);
        }
    }
    
    public static void sortByDeadline() {
    if (internshipList == null || internshipList.isEmpty()) return;
    internshipList = new LinkedList<>(mergeSort(new LinkedList<>(internshipList)));
    }

    private static LinkedList<Internship> mergeSort(LinkedList<Internship> list) {
        if (list.size() <= 1) return list;

        int mid = list.size() / 2;
        LinkedList<Internship> left = new LinkedList<>();
        LinkedList<Internship> right = new LinkedList<>();

        for (int i = 0; i < mid; i++) {
            left.add(list.get(i));
        }
        for (int i = mid; i < list.size(); i++) {
            right.add(list.get(i));
        }

        return merge(mergeSort(left), mergeSort(right));
    }

    private static LinkedList<Internship> merge(LinkedList<Internship> left, LinkedList<Internship> right) {
        LinkedList<Internship> result = new LinkedList<>();

        while (!left.isEmpty() && !right.isEmpty()) {
            String d1 = left.get(0).getDeadline();
            String d2 = right.get(0).getDeadline();

            if (formatDateForCompare(d1).compareTo(formatDateForCompare(d2)) < 0) {
                result.add(left.remove(0));
            } else {
                result.add(right.remove(0));
            }
        }
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    private static String formatDateForCompare(String date) {
        return date.replace("-Jan-", "-01-").replace("-Feb-", "-02-").replace("-Mar-", "-03-").replace("-Apr-", "-04-").replace("-May-", "-05-").replace("-Jun-", "-06-").replace("-Jul-", "-07-").replace("-Aug-", "-08-").replace("-Sep-", "-09-").replace("-Oct-", "-10-").replace("-Nov-", "-11-").replace("-Dec-", "-12-"); 
    }
}
