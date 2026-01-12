    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package model;

    /**
     *
     * @author lenovo
     */
    public class Application {
        private String studentId;
        private String internshipId;
        private String internshipTitle;
        private String companyName;
        private String status; // "Pending", "Accepted", "Rejected"
        private String applicationDate;

        public Application(String studentId, String internshipId, String internshipTitle, String companyName, String applicationDate) {
            this.studentId = studentId;
            this.internshipId = internshipId;
            this.internshipTitle = internshipTitle;
            this.companyName = companyName;
            this.applicationDate = applicationDate;
            this.status = "Pending";
        }

        // Getters and Setters
        public String getStudentId() {
            return studentId;
        }

        public String getInternshipId() {
            return internshipId;
        }

        public String getInternshipTitle() {
            return internshipTitle;
        }

        public void setInternshipTitle(String internshipTitle) {
            this.internshipTitle = internshipTitle;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(String applicationDate) {
            this.applicationDate = applicationDate;
        }

        // Generate unique key for this application
        public String getApplicationKey() {
            return studentId + "_" + internshipId;
        }

    }
