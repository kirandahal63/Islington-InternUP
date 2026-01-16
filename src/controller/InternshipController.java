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
    public static int sizeQueue = 8;
    public static Internship[] queueInternshipList = new Internship[sizeQueue];
    
    public static int front = -1;
    public static int rear = -1;
    static final int stackMax= 50;
    public static Internship[] deletedInternshipStack = new Internship[stackMax];
    public static int top = -1;

    
    public static void addDefaultInternship ()
    {
        internshipList = new LinkedList<>();

        // Pre-added internships
        Internship i1 = new Internship(
                "Java Intern",
                "ING Tech",
                "2026-Jan-30",
                15000,
                "On site",
                "3 Months",
                "Work on Java projects with senior developers.",
                "Java, OOP, Experience on Netbeans"
        );
        internshipList.add(i1);
        enqueue(i1);

        Internship i2 = new Internship(
                "Web Intern",
                "Webify",
                "2026-Jan-25",
                12000,
                "Remote",
                "6 Months",
                "Assist in frontend web development tasks.",
                "HTML, CSS,  JS"
        );
        internshipList.add(i2);
        enqueue(i2);
        
        Internship i3 = new Internship(
                "UI/UX Intern",
                "Design Hub Nationals",
                "2026-Feb-02",
                25000,
                "Remote",
                "1 Month",
                "UI research and design.",
                "Figma, Canva, Photoshop, creativity"
        );
        internshipList.add(i3);
        enqueue(i3);
        
        Internship i4 = new Internship(
                "Data Analyst Intern",
                "DataSite",
                "2026-Feb-28",
                35000,
                "International",
                "6 Months",
                "Data analysis and reports.",
                "Excel, SQL basics, Power BI"
        );
        internshipList.add(i4);
        enqueue(i4);
        
        Internship i5 = new Internship(
                "AI Intern",
                "Skill Academy",
                "2026-Jan-24",
                25000,
                "Remote",
                "1 Months",
                "Machine learning models",
                "Python, pyCharm, ML basics"
        );
        internshipList.add(i5);
        enqueue(i5);
        
        Internship i6 = new Internship(
                "Generative AI Instructor",
                "Danson Solutions",
                "2026-Jan-29",
                20000,
                "Onsite",
                "3 Months",
                "Teach core GenAI concepts in a simple, practical way",
                "Good understanding of Generative AI concepts"
        );
        internshipList.add(i6);
        enqueue(i6);
        
        Internship i7 = new Internship(
                "AI Intern",
                "Webify",
                "2026-Feb-16",
                27000,
                "Remote",
                "1 Months",
                "Machine learning models",
                "Python, pyCharm, ML basics"
        );
        internshipList.add(i7);
        enqueue(i7);
        
        Internship i8 = new Internship(
                "Software Engineer",
                "F1 Soft",
                "2026-Jan-27",
                25000,
                "Onsite",
                "3 Months",
                "Software engineering concepts",
                "Good understanding SE, and related tools."
        );
        internshipList.add(i8);
        enqueue(i8);
    }
    
    public static void addInternship(String title, String company, String deadline,int salary, String type, String duration, String description, String requirement)
    {
        Internship internship = new Internship(title, company, deadline,salary, type, duration, description, requirement);
        internshipList.add(internship);
    }
    
    public static void enqueue(Internship item) {
        if (rear+1== sizeQueue) {
            dequeue();
        }

        if (front == -1) { 
            front = rear = 0;
            queueInternshipList[rear] = item;
        } else if (rear == queueInternshipList.length - 1 && front != 0) {
            rear = 0;
            queueInternshipList[rear] = item;
        } else {
            rear++;
            queueInternshipList[rear] = item;
        }
    }

    public static Internship dequeue() {
        if (front == -1) {
            return null; 
        }

        Internship data = queueInternshipList[front];
        queueInternshipList[front] = null; 

        if (front == rear) {
            front = rear = -1;
        } else if (front == queueInternshipList.length - 1) {
            front = 0;
        } else {
            front++;
        }

        return data;
    }
    
    public static boolean isDuplicate(String title, String company, String deadline, int salary, String type, String duration, String description, String requirement) 
    {
        for (Internship in : internshipList) {
            if (in.getTitle().trim().equalsIgnoreCase(title.trim()) 
                && in.getCompany().trim().equalsIgnoreCase(company.trim()) 
                && in.getSalary() == salary
                && in.getType().trim().equalsIgnoreCase(type.trim()) 
                && in.getDuration().trim().equalsIgnoreCase(duration.trim()) 
                && in.getDescription().trim().equalsIgnoreCase(description.trim()) 
                && in.getRequirement().trim().equalsIgnoreCase(requirement.trim())) {
                return true; 
            }
        }
        return false; 
    }
    
    public static boolean deleteInternship(int index) {
        if (index >= 0 && index < internshipList.size()) {

            Internship removed = internshipList.remove(index);

            if (top == stackMax- 1) {
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
        return date.replace("-Jan-", "-01-").replace("-Feb-", "-02-").replace("-Mar-", "-03-").replace("-Apr-", "-04-")
                   .replace("-May-", "-05-").replace("-Jun-", "-06-").replace("-Jul-", "-07-").replace("-Aug-", "-08-")
                   .replace("-Sep-", "-09-").replace("-Oct-", "-10-").replace("-Nov-", "-11-").replace("-Dec-", "-12-"); 
    }
    
    public static LinkedList<Internship> findSearch(String searchTxt) {
        LinkedList<Internship> finalResults = new LinkedList<>();

        if (searchTxt.toUpperCase().startsWith("EPD")) {
            Internship result = binarySearch(internshipList, searchTxt, "ID");
            if (result != null) {
                finalResults.add(result);
                return finalResults; 
            }
        } 
        Internship titleResult = binarySearch(internshipList, searchTxt, "Title");
        if (titleResult != null) {
            finalResults.add(titleResult);
            return finalResults;
        }
        return linearSearch(searchTxt);
    }
    
    public static Internship binarySearch(LinkedList<Internship> list, String target, String category) {
        if (category.equals("ID")) {
            sortById();
        } 
        else {
            sortByTitle();
        }

        int low = 0;
        int high = internshipList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Internship midInternship = internshipList.get(mid);
            String categoryValue;
            
            if (category.equals("ID")) 
            {
                categoryValue = midInternship.getId();
            } else {
                categoryValue = midInternship.getTitle();
            }

            int matchResult = categoryValue.compareToIgnoreCase(target);
            if (matchResult == 0) {
                return midInternship;
            }else if (matchResult < 0) {
                low = mid + 1; 
            } else {
                high = mid - 1; 
            }
        }
        return null;
    }
    
    public static LinkedList<Internship> linearSearch(String query) {
        LinkedList<Internship> matchedResults = new LinkedList<>();
        String checkFor = query.toLowerCase();
        for (Internship i : internshipList) {
            if (i.getTitle().toLowerCase().contains(checkFor) || i.getCompany().toLowerCase().contains(checkFor)
                || i.getType().toLowerCase().contains(checkFor)|| i.getDeadline().toLowerCase().contains(checkFor)  ) {
                matchedResults.add(i);
            }
        }
        return matchedResults;
    }
    
    
    
    
    
    public static void refreshQueue() {
        front = -1;
        rear = -1;
        queueInternshipList = new Internship[sizeQueue];

        int start = internshipList.size() - sizeQueue;
        if (start < 0) {
            start = 0;
        }

        for (int i = start; i < internshipList.size(); i++) {
            enqueue(internshipList.get(i));
        }
    }
}
