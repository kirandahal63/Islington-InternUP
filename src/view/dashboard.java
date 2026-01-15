/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import java.awt.Color;
import model.Internship;
import controller.AdminController;
import controller.ApplicationController;
import controller.InternshipController;
import controller.StudentController;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


/**
 *  *
 * @author lenovo
 */
public class dashboard extends javax.swing.JFrame {
    private Image scaled;
    private Color defaultColor = new Color(255, 255, 255);   
    private Color normalColor = new Color(220, 220, 220);
    private Color activeColor  = new Color(153,153,255);
    private boolean isUpdateMode = false;
    private boolean isViewMode = false;
    private int editingIndex = -1;
    private Internship selectedInternship = null;

    DefaultTableModel activeTableModel;
    DefaultTableModel archivedTableModel;
    
    public dashboard() {
        initComponents();
        activeTableModel = (DefaultTableModel) internshipTable.getModel();
        loadInternshipsToTable();
        
        archivedTableModel = (DefaultTableModel) deletedInternshipTable.getModel();
        loadDeletedInternshipsToTable();
        ApplicationController.applyInternship("NP01", "EPD1");
        ApplicationController.applyInternship("NP02", "EPD1");
        loadAppliedInternships();
        loadStudentInternshipCards();
        
        btnSearchAdmin.setContentAreaFilled(false); 
        btnSearchAdmin.setOpaque(true);            
        btnSearchAdmin.setBackground(Color.WHITE); 
        btnSearchAdmin.setBorderPainted(false);
        placeholder( "Search...");
        
        studentCardContainerPanel.setLayout(new GridBagLayout());
        studentCardContainerPanel.setBackground(new Color(245, 245, 245));
        studentCardContainerPanel.setLayout(new GridLayout(0, 3, 15, 15)); 
        
        internshipScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        internshipScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        loadInternshipCards() ;
           
    }
    
    private void placeholder(String placeholderText) {

        searchField.setText(placeholderText);
        searchField.setForeground(java.awt.Color.GRAY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals(placeholderText)) {
                    searchField.setText("");
                    searchField.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(java.awt.Color.GRAY);
                    searchField.setText(placeholderText);
                }
            }
        });
        
        sSearchField.setText(placeholderText);
        sSearchField.setForeground(java.awt.Color.GRAY);
        sSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (sSearchField.getText().equals(placeholderText)) {
                    sSearchField.setText("");
                    sSearchField.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (sSearchField.getText().isEmpty()) {
                    sSearchField.setForeground(java.awt.Color.GRAY);
                    sSearchField.setText(placeholderText);
                }
            }
        });
    }
    private void resetToPlaceholder() {
        sSearchField.setText("Search...");
        sSearchField.setForeground(java.awt.Color.GRAY);
        searchField.setText("Search...");
        searchField.setForeground(java.awt.Color.GRAY);
        this.requestFocusInWindow();
}
    
    private void setNumber(){
        int studentCount = StudentController.studentMap.size();
        int internshipCount = InternshipController.internshipList.size();
        int applicationCount = ApplicationController.totalApplicationCount();
        noOfStudent.setText(String.valueOf(studentCount));
        noOfInternship.setText(String.valueOf(internshipCount));
        noOfInternship1.setText(String.valueOf(internshipCount));
        noOfApplications.setText(String.valueOf(applicationCount));
        if (StudentController.currentStudentId != null) {
            int count = ApplicationController.individualStudentApplicationCount(StudentController.currentStudentId);
            indiviudalNoOfApplications.setText(String.valueOf(count));
        } else {
            return;
        }
        Dimension size = new Dimension(60, 48); 
        noOfStudent.setPreferredSize(size);noOfInternship.setPreferredSize(size);noOfInternship1.setPreferredSize(size);noOfApplications.setPreferredSize(size);indiviudalNoOfApplications.setPreferredSize(size);
        noOfStudent.setMinimumSize(size);noOfInternship.setMinimumSize(size);noOfInternship1.setMinimumSize(size);noOfApplications.setMinimumSize(size);indiviudalNoOfApplications.setMinimumSize(size);
    }
    private void setStudentDetails(){
        String currentId = controller.StudentController.getCurrentStudentId();
    
    if (currentId != null) {
        model.Student student = controller.StudentController.studentMap.get(currentId);        
        if (student != null) {
            settingsName.setText(student.getStudentName().toUpperCase());
            settingCollegeID.setText(student.getStudentId().toUpperCase());
            settingsEmail.setText(student.getEmail());
            settingsPass.setText(student.getPassword());
        }
    } else {
        settingsName.setText("");
        settingCollegeID.setText("");
        settingsEmail.setText("");
        settingsPass.setText("");
    }
    }
    
    private void loadInternshipsToTable(){
    activeTableModel.setRowCount(0);
        for (Internship i : InternshipController.internshipList) {

            Object[] row = {
                i.getId(),
                i.getTitle(),
                i.getCompany(),
                i.getType(),
                i.getDuration(),
                i.getDeadline()
            };
            activeTableModel.addRow(row);
        }
    }
    
    private void loadDeletedInternshipsToTable() {
        archivedTableModel.setRowCount(0);
        for (int i = InternshipController.top; i >= 0; i--) {
            Internship in = InternshipController.deletedInternshipStack[i];
            Object[] row = {
                in.getId(),
                in.getTitle(),
                in.getCompany(),
                in.getType(),
                in.getDuration(),
                in.getDeadline()
            };
            archivedTableModel.addRow(row);
        }
    }
    
    public void loadAppliedInternships() {
        DefaultTableModel model = (DefaultTableModel) appliedInternshipsTable.getModel();
        model.setRowCount(0); 

        for (Internship internship : InternshipController.internshipList) {
            String ID = internship.getId();

            if (ApplicationController.viewAllApplications().containsKey(ID)) {

            int applicantCount = ApplicationController.viewAllApplications().get(ID).size();
            model.addRow(new Object[]{
                ID,
                internship.getTitle(),                        
                internship.getCompany(),            
                internship.getDeadline(),     
                applicantCount         
            });
            
            applicationNo.setText(String.valueOf(ApplicationController.totalApplicationCount()));
        }
        }
    }
    
    private void resetNavColors() {
        dashboardNav.setBackground(defaultColor);
        manageNav.setBackground(defaultColor);
        archiveNav.setBackground(defaultColor);
        settingsNav.setBackground(defaultColor);
        applicationNav.setBackground(defaultColor);
        logoutNav.setBackground(defaultColor);
        studentDashboardNav.setBackground(defaultColor);
        viewInternship.setBackground(defaultColor);
        studentSettings.setBackground(defaultColor);
    }
    private void resetSortButtonColors() {
        titleButton.setBackground(defaultColor);
        companyButton.setBackground(defaultColor);
        durationButton.setBackground(defaultColor);
        deadlineButton.setBackground(defaultColor);
        sTitleButton.setBackground(defaultColor);
        sCompanyButton.setBackground(defaultColor);
        sTypeButton.setBackground(defaultColor);
        sDeadlineButton.setBackground(defaultColor);
    }

    private void loadInternshipCards(){
        cardContainerPanel.removeAll();
        cardContainerPanel.setLayout(new GridLayout(0, 2, 15, 15));
        for (int i = 0; i < InternshipController.sizeQueue; i++) {
            Internship internship = InternshipController.queueInternshipList[i];

            if (internship != null) {
                JPanel card = createInternshipCard(internship);
                cardContainerPanel.add(card);
            }
        }
        cardContainerPanel.revalidate();
        cardContainerPanel.repaint();
    }
    
    private void loadRecentInternships() {        
        cardContainerPanel1.removeAll();
        cardContainerPanel1.setLayout(new GridLayout(0, 2, 15, 15));
        for (int i = 0; i < InternshipController.sizeQueue; i++) {
                Internship internship = InternshipController.queueInternshipList[i];

                if (internship != null) {
                    JPanel card = createInternshipCard(internship);
                    cardContainerPanel1.add(card);
                }
            }
        cardContainerPanel1.revalidate();
        cardContainerPanel1.repaint();
    }
    
    private JPanel createInternshipCard(Internship i) {
        
        JPanel card = new JPanel();  
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        card.setPreferredSize(new Dimension(320, 80));
        card.setMaximumSize(new Dimension(320, 130));
        card.setMinimumSize(new Dimension(320, 130));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(normalColor, 1),BorderFactory.createEmptyBorder(10,10,10,10)));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activeColor, 3),BorderFactory.createEmptyBorder(10,10,10,10)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(normalColor, 1),BorderFactory.createEmptyBorder(10,10,10,10)));
            }
        });

        JLabel titleLabel = new JLabel(i.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel companyLabel = new JLabel(i.getCompany());
        companyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel detailLabel = new JLabel("Type: " + i.getType() + " | Deadline: " + i.getDeadline());
        detailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        detailLabel.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.setBackground(Color.WHITE);

        textPanel.add(titleLabel);
        textPanel.add(companyLabel);
        textPanel.add(detailLabel);

        card.add(textPanel, BorderLayout.CENTER);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (StudentController.currentStudentId == null) { 
                    CardLayout cl = (CardLayout)(cardPanel.getLayout());
                    cl.show(cardPanel, "card3"); 
                    resetNavColors();
                    manageNav.setBackground(activeColor);
                } else {
                    CardLayout cl = (CardLayout)(cardPanel1.getLayout());
                    cl.show(cardPanel1, "card4"); 
                    loadStudentInternshipCards();
                    resetNavColors();
                    viewInternship.setBackground(activeColor);
                }                
            }
        });

        return card;
    }
   
private void loadStudentInternshipCards() {
    studentCardContainerPanel.removeAll();
    studentCardContainerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int col = 0;
    int row = 0;

    for (Internship internship : InternshipController.internshipList) {
        JPanel card = createStudentInternshipCard(internship);
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        studentCardContainerPanel.add(card, gbc);

        col++;
        if (col == 3) { 
            col = 0;
            row++;
        }
    }

    gbc.gridx = 0;
    gbc.gridy = row + 1;
    gbc.weighty = 1;
    studentCardContainerPanel.add(Box.createVerticalGlue(), gbc);
    studentCardContainerPanel.revalidate();
    studentCardContainerPanel.repaint();
}
private void displayStudentSearchResults(LinkedList<Internship> results) {
    studentCardContainerPanel.removeAll();
    studentCardContainerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.anchor = GridBagConstraints.NORTHWEST;
    int col = 0;
    int row = 0;
    for (Internship internship : results) {
        JPanel card = createStudentInternshipCard(internship);
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        studentCardContainerPanel.add(card, gbc);
        col++;
        if (col == 3) { 
            col = 0;
            row++;
        }
    }
    gbc.gridx = 0;  gbc.gridy = row + 1;  gbc.weighty = 1;
    studentCardContainerPanel.add(Box.createVerticalGlue(), gbc);
    studentCardContainerPanel.revalidate();
    studentCardContainerPanel.repaint();
}

private JPanel createStudentInternshipCard(Internship i) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);

    card.setPreferredSize(new Dimension(250, 100)); 
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220,220,220)),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    JLabel title = new JLabel(i.getTitle());
    title.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel company = new JLabel(i.getCompany());
    company.setFont(new Font("Segoe UI", Font.PLAIN, 12));

    JLabel meta = new JLabel("Type: " + i.getType() + " | Deadline: " + i.getDeadline());
    meta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    meta.setForeground(Color.GRAY);

    JPanel textPanel = new JPanel(new GridLayout(0, 1));
    textPanel.setBackground(Color.WHITE);
    textPanel.add(title);
    textPanel.add(company);
    textPanel.add(meta);

    card.add(textPanel, BorderLayout.CENTER);

    card.setCursor(new Cursor(Cursor.HAND_CURSOR));
    card.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(activeColor, 3),
                BorderFactory.createEmptyBorder(10,10,10,10)
            ));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(normalColor, 1),
                BorderFactory.createEmptyBorder(10,10,10,10)
            ));
        }
    });
    card.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        openStudentInternshipDetails(i);
    }
    });

    return card;
}

public void openStudentInternshipDetails(Internship i) {
    selectedInternship = i;
    titleFieldS.setText(i.getTitle());
    companyFieldS.setText(i.getCompany());
    salaryFieldS.setText(String.valueOf(i.getSalary()));
    descriptionAreaS.setText(i.getDescription());
    skillAreaS.setText(i.getRequirement());
    typeComboS.setSelectedItem(i.getType());
    durationComboS.setSelectedItem(i.getDuration());
    String[] date = i.getDeadline().split("-");
    yearComboS.setSelectedItem(date[0]);
    monthComboS.setSelectedItem(date[1]);
    dayCombo1.setSelectedItem(date[2]);
    
    titleFieldS.setEnabled(true);titleFieldS.setEditable(false);titleFieldS.setFocusable(false);titleFieldS.setBackground(Color.WHITE);titleFieldS.setForeground(Color.BLACK);
    companyFieldS.setEnabled(true);companyFieldS.setEditable(false);companyFieldS.setFocusable(false);companyFieldS.setBackground(Color.WHITE);companyFieldS.setForeground(Color.BLACK);
    skillAreaS.setEnabled(true);skillAreaS.setEditable(false);skillAreaS.setFocusable(false);skillAreaS.setBackground(Color.WHITE);skillAreaS.setForeground(Color.BLACK);
    salaryFieldS.setEnabled(true);salaryFieldS.setEditable(false);salaryFieldS.setFocusable(false);salaryFieldS.setBackground(Color.WHITE);salaryFieldS.setForeground(Color.BLACK);
    descriptionAreaS.setEnabled(true);descriptionAreaS.setEditable(false);descriptionAreaS.setFocusable(false);descriptionAreaS.setBackground(Color.WHITE);descriptionAreaS.setForeground(Color.BLACK);
    typeComboS.setEnabled(false);typeComboS.setEditable(false);typeComboS.setFocusable(false);typeComboS.setBackground(Color.WHITE);typeComboS.setForeground(Color.BLACK);
    durationComboS.setEnabled(false);
    yearComboS.setEnabled(false);
    monthComboS.setEnabled(false);
    dayCombo1.setEnabled(false);
        
    CardLayout cl = (CardLayout) cardPanel1.getLayout();
    cl.show(cardPanel1, "card5");
    }

    private void clearloginFields(){
        collegeIDField.setText("");
        passWordField.setText(""); 
        jTextField2.setText("");  
        passwordField.setText("");  
        collegeField.setText("");  
        nameField.setText(""); 
        passField.setText("");         
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        parentcard = new javax.swing.JPanel();
        Login = new javax.swing.JPanel();
        sideImagePanel = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        right = new javax.swing.JPanel();
        left = new javax.swing.JPanel();
        south = new javax.swing.JPanel();
        cardPanel2 = new javax.swing.JPanel();
        AdminPanel = new javax.swing.JPanel();
        jPanel98 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jPanel99 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jLabel39 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        StudentPanel = new javax.swing.JPanel();
        jPanel100 = new javax.swing.JPanel();
        jPanel101 = new javax.swing.JPanel();
        jPanel102 = new javax.swing.JPanel();
        jPanel103 = new javax.swing.JPanel();
        jPanel104 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        collegeIDField = new javax.swing.JTextField();
        passWordField = new javax.swing.JPasswordField();
        jLabel80 = new javax.swing.JLabel();
        studentLogin = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        SignUP = new javax.swing.JPanel();
        jPanel105 = new javax.swing.JPanel();
        jPanel106 = new javax.swing.JPanel();
        jPanel107 = new javax.swing.JPanel();
        jPanel108 = new javax.swing.JPanel();
        jPanel109 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        collegeField = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        signUpButton = new javax.swing.JButton();
        nameField = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        jPanel21 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        adminButton = new javax.swing.JButton();
        studentButton = new javax.swing.JButton();
        logo_panel = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        Admin = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        btnSearchAdmin = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        gridPanel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        noOfStudent = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        noOfInternship = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        noOfApplications = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        internshipPreviewPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cardContainerPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        manageInternship = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        titleButton = new javax.swing.JButton();
        companyButton = new javax.swing.JButton();
        durationButton = new javax.swing.JButton();
        deadlineButton = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        internshipTable = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        viewDetails = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        addNewInternship = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        companyField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        dayCombo = new javax.swing.JComboBox<>();
        monthCombo = new javax.swing.JComboBox<>();
        yearCombo = new javax.swing.JComboBox<>();
        salaryField = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        typeCombo = new javax.swing.JComboBox<>();
        durationCombo = new javax.swing.JComboBox<>();
        jPanel39 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        add_updateButton = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        skillArea = new javax.swing.JTextArea();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionArea = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        deletedPanel = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        restoreButton = new javax.swing.JButton();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        deletedInternshipTable = new javax.swing.JTable();
        applicationsView = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        appliedInternshipsTable = new javax.swing.JTable();
        jPanel110 = new javax.swing.JPanel();
        jPanel111 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        applicationNo = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        studentApplicationTable = new javax.swing.JTable();
        settingPanel = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        AdminNav = new javax.swing.JPanel();
        adminNavigation = new javax.swing.JPanel();
        dashboardNav = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        manageNav = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        archiveNav = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        applicationNav = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        settingsNav = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        logoutNav = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Student = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        sSearchField = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        btnSearch1 = new javax.swing.JButton();
        jPanel65 = new javax.swing.JPanel();
        studentNav1 = new javax.swing.JPanel();
        adminNavigation3 = new javax.swing.JPanel();
        studentDashboardNav = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        viewInternship = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        studentSettings = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        cardPanel1 = new javax.swing.JPanel();
        dashboardPanel1 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        gridPanel1 = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        noOfStudent1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel71 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        noOfInternship1 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel78 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        indiviudalNoOfApplications = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jPanel80 = new javax.swing.JPanel();
        jPanel81 = new javax.swing.JPanel();
        jPanel82 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        internshipPreviewPanel1 = new javax.swing.JPanel();
        jPanel118 = new javax.swing.JPanel();
        cardContainerPanel1 = new javax.swing.JPanel();
        jPanel119 = new javax.swing.JPanel();
        applicationsView1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        sCompanyButton = new javax.swing.JButton();
        sDeadlineButton = new javax.swing.JButton();
        sTypeButton = new javax.swing.JButton();
        sTitleButton = new javax.swing.JButton();
        jPanel72 = new javax.swing.JPanel();
        jPanel85 = new javax.swing.JPanel();
        internshipScrollPane = new javax.swing.JScrollPane();
        studentCardContainerPanel = new javax.swing.JPanel();
        studentsettingPanel1 = new javax.swing.JPanel();
        jPanel112 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jPanel113 = new javax.swing.JPanel();
        jPanel114 = new javax.swing.JPanel();
        jPanel115 = new javax.swing.JPanel();
        jPanel116 = new javax.swing.JPanel();
        jPanel117 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        settingsName = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        settingCollegeID = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        settingsEmail = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        settingsPass = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        addNewInternship1 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jPanel88 = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        titleFieldS = new javax.swing.JTextField();
        companyFieldS = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jPanel93 = new javax.swing.JPanel();
        dayCombo1 = new javax.swing.JComboBox<>();
        monthComboS = new javax.swing.JComboBox<>();
        yearComboS = new javax.swing.JComboBox<>();
        salaryFieldS = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        typeComboS = new javax.swing.JComboBox<>();
        durationComboS = new javax.swing.JComboBox<>();
        jPanel94 = new javax.swing.JPanel();
        jPanel95 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        jPanel96 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        skillAreaS = new javax.swing.JTextArea();
        jPanel97 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        descriptionAreaS = new javax.swing.JTextArea();
        jLabel78 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        parentcard.setLayout(new java.awt.CardLayout());

        Login.setLayout(new java.awt.BorderLayout());

        sideImagePanel.setBackground(new java.awt.Color(102, 102, 255));

        ImageIcon icon5 = new ImageIcon("src/images/login.jpg");
        Image img5 = icon5.getImage();
        Image scaled5 = img5.getScaledInstance(500, 909, Image.SCALE_SMOOTH);

        jLabel34.setIcon(new ImageIcon(scaled5));
        jLabel34.setPreferredSize(new java.awt.Dimension(250, 509));

        javax.swing.GroupLayout sideImagePanelLayout = new javax.swing.GroupLayout(sideImagePanel);
        sideImagePanel.setLayout(sideImagePanelLayout);
        sideImagePanelLayout.setHorizontalGroup(
            sideImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        sideImagePanelLayout.setVerticalGroup(
            sideImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );

        Login.add(sideImagePanel, java.awt.BorderLayout.LINE_START);

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setLayout(new java.awt.BorderLayout());

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setPreferredSize(new java.awt.Dimension(455, 140));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jPanel58.add(jPanel59, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(468, 360));
        jPanel1.setLayout(new java.awt.BorderLayout());

        right.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout rightLayout = new javax.swing.GroupLayout(right);
        right.setLayout(rightLayout);
        rightLayout.setHorizontalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        rightLayout.setVerticalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jPanel1.add(right, java.awt.BorderLayout.LINE_END);

        left.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jPanel1.add(left, java.awt.BorderLayout.LINE_START);

        south.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout southLayout = new javax.swing.GroupLayout(south);
        south.setLayout(southLayout);
        southLayout.setHorizontalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );
        southLayout.setVerticalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(south, java.awt.BorderLayout.PAGE_END);

        cardPanel2.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel2.setLayout(new java.awt.CardLayout());

        AdminPanel.setBackground(new java.awt.Color(255, 255, 255));
        AdminPanel.setLayout(new java.awt.BorderLayout());

        jPanel98.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel98Layout = new javax.swing.GroupLayout(jPanel98);
        jPanel98.setLayout(jPanel98Layout);
        jPanel98Layout.setHorizontalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel98Layout.setVerticalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        AdminPanel.add(jPanel98, java.awt.BorderLayout.LINE_END);

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setPreferredSize(new java.awt.Dimension(516, 80));

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        AdminPanel.add(jPanel56, java.awt.BorderLayout.PAGE_END);

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));
        jPanel51.setPreferredSize(new java.awt.Dimension(516, 30));

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        AdminPanel.add(jPanel51, java.awt.BorderLayout.PAGE_START);

        jPanel99.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel99Layout = new javax.swing.GroupLayout(jPanel99);
        jPanel99.setLayout(jPanel99Layout);
        jPanel99Layout.setHorizontalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel99Layout.setVerticalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        AdminPanel.add(jPanel99, java.awt.BorderLayout.LINE_START);

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setPreferredSize(new java.awt.Dimension(308, 100));

        jLabel38.setText("Username");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        jLabel39.setText("Password");

        jButton12.setBackground(new java.awt.Color(167, 139, 250));
        jButton12.setText("Login");
        jButton12.setBorder(null);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AdminPanel.add(jPanel43, java.awt.BorderLayout.CENTER);

        cardPanel2.add(AdminPanel, "AdminPanel");

        StudentPanel.setBackground(new java.awt.Color(255, 255, 255));
        StudentPanel.setLayout(new java.awt.BorderLayout());

        jPanel100.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel100Layout = new javax.swing.GroupLayout(jPanel100);
        jPanel100.setLayout(jPanel100Layout);
        jPanel100Layout.setHorizontalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel100Layout.setVerticalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        StudentPanel.add(jPanel100, java.awt.BorderLayout.LINE_END);

        jPanel101.setBackground(new java.awt.Color(255, 255, 255));
        jPanel101.setPreferredSize(new java.awt.Dimension(516, 40));

        javax.swing.GroupLayout jPanel101Layout = new javax.swing.GroupLayout(jPanel101);
        jPanel101.setLayout(jPanel101Layout);
        jPanel101Layout.setHorizontalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel101Layout.setVerticalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        StudentPanel.add(jPanel101, java.awt.BorderLayout.PAGE_END);

        jPanel102.setBackground(new java.awt.Color(255, 255, 255));
        jPanel102.setPreferredSize(new java.awt.Dimension(516, 30));

        javax.swing.GroupLayout jPanel102Layout = new javax.swing.GroupLayout(jPanel102);
        jPanel102.setLayout(jPanel102Layout);
        jPanel102Layout.setHorizontalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel102Layout.setVerticalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        StudentPanel.add(jPanel102, java.awt.BorderLayout.PAGE_START);

        jPanel103.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel103Layout = new javax.swing.GroupLayout(jPanel103);
        jPanel103.setLayout(jPanel103Layout);
        jPanel103Layout.setHorizontalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel103Layout.setVerticalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        StudentPanel.add(jPanel103, java.awt.BorderLayout.LINE_START);

        jPanel104.setBackground(new java.awt.Color(255, 255, 255));
        jPanel104.setPreferredSize(new java.awt.Dimension(308, 120));

        jLabel79.setText("College ID");

        collegeIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collegeIDFieldActionPerformed(evt);
            }
        });

        passWordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passWordFieldActionPerformed(evt);
            }
        });

        jLabel80.setText("Password");

        studentLogin.setBackground(new java.awt.Color(167, 139, 250));
        studentLogin.setText("Login");
        studentLogin.setBorder(null);
        studentLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentLoginActionPerformed(evt);
            }
        });

        jLabel81.setText("Don't have an account? Sign Up");
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel81.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel81MouseMoved(evt);
            }
        });
        jLabel81.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLabel81FocusLost(evt);
            }
        });
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel81MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel104Layout = new javax.swing.GroupLayout(jPanel104);
        jPanel104.setLayout(jPanel104Layout);
        jPanel104Layout.setHorizontalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel104Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passWordField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(collegeIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81))
                .addGap(181, 181, 181))
        );
        jPanel104Layout.setVerticalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel104Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collegeIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel80)
                .addGap(18, 18, 18)
                .addComponent(passWordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(studentLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        StudentPanel.add(jPanel104, java.awt.BorderLayout.CENTER);

        cardPanel2.add(StudentPanel, "student");

        SignUP.setBackground(new java.awt.Color(255, 255, 255));
        SignUP.setLayout(new java.awt.BorderLayout());

        jPanel105.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel105Layout = new javax.swing.GroupLayout(jPanel105);
        jPanel105.setLayout(jPanel105Layout);
        jPanel105Layout.setHorizontalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel105Layout.setVerticalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        SignUP.add(jPanel105, java.awt.BorderLayout.LINE_END);

        jPanel106.setBackground(new java.awt.Color(255, 255, 255));
        jPanel106.setPreferredSize(new java.awt.Dimension(516, 40));

        javax.swing.GroupLayout jPanel106Layout = new javax.swing.GroupLayout(jPanel106);
        jPanel106.setLayout(jPanel106Layout);
        jPanel106Layout.setHorizontalGroup(
            jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel106Layout.setVerticalGroup(
            jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        SignUP.add(jPanel106, java.awt.BorderLayout.PAGE_END);

        jPanel107.setBackground(new java.awt.Color(255, 255, 255));
        jPanel107.setPreferredSize(new java.awt.Dimension(516, 30));

        javax.swing.GroupLayout jPanel107Layout = new javax.swing.GroupLayout(jPanel107);
        jPanel107.setLayout(jPanel107Layout);
        jPanel107Layout.setHorizontalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel107Layout.setVerticalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        SignUP.add(jPanel107, java.awt.BorderLayout.PAGE_START);

        jPanel108.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel108Layout = new javax.swing.GroupLayout(jPanel108);
        jPanel108.setLayout(jPanel108Layout);
        jPanel108Layout.setHorizontalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel108Layout.setVerticalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        SignUP.add(jPanel108, java.awt.BorderLayout.LINE_START);

        jPanel109.setBackground(new java.awt.Color(255, 255, 255));
        jPanel109.setPreferredSize(new java.awt.Dimension(308, 140));

        jLabel40.setText("College ID");

        collegeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collegeFieldActionPerformed(evt);
            }
        });

        jLabel62.setText("Full Name");

        signUpButton.setBackground(new java.awt.Color(167, 139, 250));
        signUpButton.setText("Sign Up");
        signUpButton.setBorder(null);
        signUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonActionPerformed(evt);
            }
        });

        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        jLabel63.setText("Password");

        passField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel109Layout = new javax.swing.GroupLayout(jPanel109);
        jPanel109.setLayout(jPanel109Layout);
        jPanel109Layout.setHorizontalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel109Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(collegeField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63)
                    .addGroup(jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(passField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signUpButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel109Layout.setVerticalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel109Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collegeField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SignUP.add(jPanel109, java.awt.BorderLayout.CENTER);

        cardPanel2.add(SignUP, "SignUP");

        jPanel1.add(cardPanel2, java.awt.BorderLayout.CENTER);

        jPanel21.setPreferredSize(new java.awt.Dimension(614, 230));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));

        adminButton.setText("Admin");
        adminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminButtonActionPerformed(evt);
            }
        });

        studentButton.setText("Student");
        studentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(adminButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(studentButton)
                .addContainerGap(333, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addGap(0, 107, Short.MAX_VALUE)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminButton)
                    .addComponent(studentButton)))
        );

        jPanel21.add(jPanel60, java.awt.BorderLayout.CENTER);

        logo_panel.setBackground(new java.awt.Color(255, 255, 255));

        ImageIcon icon13 = new ImageIcon("src/images/internupLogo.png");
        Image imgg3 = icon13.getImage();
        Image scaledup10 = imgg3.getScaledInstance(160, 190, Image.SCALE_SMOOTH);
        jLabel36.setIcon(new ImageIcon(scaledup10));

        ImageIcon icon68 = new ImageIcon("src/images/collegeLogo.png");
        Image imgg91 = icon68.getImage();
        Image scaled91 = imgg91.getScaledInstance(170, 120, Image.SCALE_SMOOTH);
        jLabel68.setIcon(new ImageIcon(scaled91));

        javax.swing.GroupLayout logo_panelLayout = new javax.swing.GroupLayout(logo_panel);
        logo_panel.setLayout(logo_panelLayout);
        logo_panelLayout.setHorizontalGroup(
            logo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logo_panelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        logo_panelLayout.setVerticalGroup(
            logo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logo_panelLayout.createSequentialGroup()
                .addGroup(logo_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 42, Short.MAX_VALUE))
        );

        jPanel21.add(logo_panel, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel21, java.awt.BorderLayout.PAGE_START);

        jPanel58.add(jPanel1, java.awt.BorderLayout.CENTER);

        Login.add(jPanel58, java.awt.BorderLayout.CENTER);

        parentcard.add(Login, "card4");

        Admin.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(933, 80));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Hello");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel17.setText("Admin");

        searchField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        searchField.setPreferredSize(new java.awt.Dimension(64, 25));
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });

        ImageIcon icon55 = new ImageIcon("src/images/internupLogo.png");
        Image imgg55 = icon55.getImage();
        Image scaledup55 = imgg55.getScaledInstance(160, 190, Image.SCALE_SMOOTH);
        jLabel4.setIcon(new ImageIcon(scaledup55));

        ImageIcon icon83 = new ImageIcon("src/images/user.png");
        Image img83 = icon83.getImage();
        Image scaled83 = img83.getScaledInstance(41, 34, Image.SCALE_SMOOTH);
        jLabel83.setIcon(new ImageIcon(scaled83));
        jLabel83.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        ImageIcon iconn = new ImageIcon("src/images/searchlogo.png");
        Image imgS = iconn.getImage();
        Image scaledB = imgS.getScaledInstance(42, 36, Image.SCALE_SMOOTH);
        btnSearchAdmin.setIcon(new ImageIcon(scaledB));
        btnSearchAdmin.setBorder(null);
        btnSearchAdmin.setBorderPainted(false);
        btnSearchAdmin.setContentAreaFilled(false);
        btnSearchAdmin.setFocusPainted(false);
        btnSearchAdmin.setFocusable(false);
        btnSearchAdmin.setRequestFocusEnabled(false);
        btnSearchAdmin.setVerifyInputWhenFocusTarget(false);
        btnSearchAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 528, Short.MAX_VALUE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnSearchAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
                        .addGap(23, 23, 23))))
        );

        Admin.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        cardPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel.setLayout(new java.awt.CardLayout());

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 204));
        dashboardPanel.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(763, 150));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(20, 100));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel16, java.awt.BorderLayout.EAST);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(20, 100));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel18, java.awt.BorderLayout.LINE_START);

        gridPanel.setBackground(new java.awt.Color(255, 255, 255));
        gridPanel.setLayout(new java.awt.GridLayout(1, 4, 20, 0));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Students");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        noOfStudent.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        noOfStudent.setForeground(new java.awt.Color(255, 255, 255));
        noOfStudent.setText("5");
        jPanel13.add(noOfStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 50, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel7.setText("jLabel4");
        jPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 160));

        gridPanel.add(jPanel13);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Partner Companies");
        jPanel11.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("200+");
        jPanel11.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel6.setText("jLabel4");
        jPanel11.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));

        gridPanel.add(jPanel11);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Active Internship ");
        jPanel14.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        noOfInternship.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        noOfInternship.setForeground(new java.awt.Color(255, 255, 255));
        noOfInternship.setText("8");
        jPanel14.add(noOfInternship, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 50, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jPanel14.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 150));

        gridPanel.add(jPanel14);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("No of Application Received");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        noOfApplications.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        noOfApplications.setForeground(new java.awt.Color(255, 255, 255));
        noOfApplications.setText("5");
        jPanel9.add(noOfApplications, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 50, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel5.setText("jLabel4");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 151));

        gridPanel.add(jPanel9);

        jPanel17.add(gridPanel, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel17, java.awt.BorderLayout.CENTER);

        dashboardPanel.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(153, 153, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(300, 392));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel74.setBackground(new java.awt.Color(255, 255, 255));
        jPanel74.setPreferredSize(new java.awt.Dimension(300, 112));

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jPanel19.add(jPanel74, java.awt.BorderLayout.PAGE_END);

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setPreferredSize(new java.awt.Dimension(300, 494));
        jPanel44.setVerifyInputWhenFocusTarget(false);

        ImageIcon icon91 = new ImageIcon("src/images/companies.png");
        Image imgg1 = icon91.getImage();
        Image scaledCom = imgg1.getScaledInstance(266, 479, Image.SCALE_SMOOTH);
        jLabel67.setIcon(new ImageIcon(scaledCom));
        jLabel67.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel19.add(jPanel44, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel19, java.awt.BorderLayout.LINE_END);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(704, 464));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Recent Intenships");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel33)
                .addContainerGap(469, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(14, 14, 14))
        );

        jPanel20.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        internshipPreviewPanel.setBackground(new java.awt.Color(255, 255, 255));
        internshipPreviewPanel.setPreferredSize(new java.awt.Dimension(714, 341));
        internshipPreviewPanel.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(40, 341));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        internshipPreviewPanel.add(jPanel8, java.awt.BorderLayout.LINE_START);

        cardContainerPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardContainerPanel.setLayout(new java.awt.GridLayout(1, 2, 15, 15));
        internshipPreviewPanel.add(cardContainerPanel, java.awt.BorderLayout.CENTER);

        jPanel20.add(internshipPreviewPanel, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jPanel20.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel15.add(jPanel20, java.awt.BorderLayout.CENTER);

        dashboardPanel.add(jPanel15, java.awt.BorderLayout.CENTER);

        cardPanel.add(dashboardPanel, "dashboard");

        manageInternship.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setPreferredSize(new java.awt.Dimension(904, 60));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.GridLayout(1, 4, 1, 1));

        titleButton.setText("Sort By Title");
        titleButton.setPreferredSize(new java.awt.Dimension(150, 30));
        titleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titleButtonMouseClicked(evt);
            }
        });
        titleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleButtonActionPerformed(evt);
            }
        });

        companyButton.setText("Sort By Company");
        companyButton.setPreferredSize(new java.awt.Dimension(150, 30));
        companyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                companyButtonMouseClicked(evt);
            }
        });
        companyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyButtonActionPerformed(evt);
            }
        });

        durationButton.setText("Sort by Duration");
        durationButton.setPreferredSize(new java.awt.Dimension(150, 30));
        durationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                durationButtonMouseClicked(evt);
            }
        });
        durationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                durationButtonActionPerformed(evt);
            }
        });

        deadlineButton.setText("Sort by Deadline");
        deadlineButton.setPreferredSize(new java.awt.Dimension(150, 30));
        deadlineButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deadlineButtonMouseClicked(evt);
            }
        });
        deadlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deadlineButtonActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 153, 255));
        jButton7.setText("+ Add New Internship");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(titleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(companyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(durationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(companyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(titleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(durationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        manageInternship.add(jPanel23, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel27, java.awt.BorderLayout.LINE_START);

        internshipTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Title ", "Company", "Type", "Duration", "Deadline"
            }
        ));
        internshipTable.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane5.setViewportView(internshipTable);

        jPanel10.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setPreferredSize(new java.awt.Dimension(20, 467));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel29, java.awt.BorderLayout.LINE_END);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setPreferredSize(new java.awt.Dimension(904, 20));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel28, java.awt.BorderLayout.PAGE_START);

        manageInternship.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setForeground(new java.awt.Color(204, 255, 255));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setLayout(new java.awt.GridLayout(1, 0, 30, 0));

        viewDetails.setText("View Details");
        viewDetails.setPreferredSize(new java.awt.Dimension(150, 23));
        viewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsActionPerformed(evt);
            }
        });
        jPanel33.add(viewDetails);

        jButton9.setText("Edit Details");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel33.add(jButton9);

        jButton10.setText("Delete");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel33.add(jButton10);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel30, java.awt.BorderLayout.CENTER);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setPreferredSize(new java.awt.Dimension(904, 30));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel32, java.awt.BorderLayout.PAGE_END);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setPreferredSize(new java.awt.Dimension(904, 30));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel31, java.awt.BorderLayout.PAGE_START);

        manageInternship.add(jPanel24, java.awt.BorderLayout.PAGE_END);

        cardPanel.add(manageInternship, "card3");

        addNewInternship.setBackground(new java.awt.Color(255, 255, 255));
        addNewInternship.setLayout(new java.awt.BorderLayout());

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setPreferredSize(new java.awt.Dimension(817, 40));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        addNewInternship.add(jPanel34, java.awt.BorderLayout.PAGE_START);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new java.awt.BorderLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
        );

        jPanel35.add(jPanel22, java.awt.BorderLayout.WEST);

        jPanel36.setLayout(new java.awt.BorderLayout());

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
        );

        jPanel36.add(jPanel26, java.awt.BorderLayout.LINE_END);

        jPanel37.setLayout(new java.awt.BorderLayout());

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setPreferredSize(new java.awt.Dimension(617, 210));
        jPanel38.setLayout(new java.awt.GridLayout(6, 2, 50, 5));

        jLabel23.setText("Title");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel23.setInheritsPopupMenu(false);
        jPanel38.add(jLabel23);

        jLabel24.setText("Company");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel38.add(jLabel24);

        titleField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        titleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleFieldActionPerformed(evt);
            }
        });
        jPanel38.add(titleField);

        companyField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel38.add(companyField);

        jLabel25.setText("Deadline");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel38.add(jLabel25);

        jLabel26.setText("Salary");
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel38.add(jLabel26);

        jPanel57.setLayout(new java.awt.GridLayout(1, 3));

        dayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dayCombo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        dayCombo.setPreferredSize(new java.awt.Dimension(82, 22));
        jPanel57.add(dayCombo);

        monthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        jPanel57.add(monthCombo);

        yearCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year", "2025", "2026", "2027" }));
        yearCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboActionPerformed(evt);
            }
        });
        jPanel57.add(yearCombo);

        jPanel38.add(jPanel57);

        salaryField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel38.add(salaryField);

        jLabel27.setText("Internship Type");
        jLabel27.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel38.add(jLabel27);

        jLabel28.setText("Duration");
        jLabel28.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel38.add(jLabel28);

        typeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Remote", "On Site", "International" }));
        jPanel38.add(typeCombo);

        durationCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Month", "3 Months", "6 Months", "12 Months ", "36 Months" }));
        durationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                durationComboActionPerformed(evt);
            }
        });
        jPanel38.add(durationCombo);

        jPanel37.add(jPanel38, java.awt.BorderLayout.PAGE_START);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setPreferredSize(new java.awt.Dimension(617, 50));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        cancelButton.setText("Cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(120, 30));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel41.add(cancelButton);

        add_updateButton.setBackground(new java.awt.Color(153, 153, 255));
        add_updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_updateButtonActionPerformed(evt);
            }
        });
        jPanel41.add(add_updateButton);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap(466, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel37.add(jPanel39, java.awt.BorderLayout.PAGE_END);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new java.awt.BorderLayout());

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Description");
        jLabel29.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel29.setPreferredSize(new java.awt.Dimension(43, 50));
        jPanel40.add(jLabel29, java.awt.BorderLayout.PAGE_START);

        skillArea.setColumns(20);
        skillArea.setRows(5);
        jScrollPane1.setViewportView(skillArea);

        jPanel40.add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setLayout(new java.awt.BorderLayout());

        descriptionArea.setColumns(20);
        descriptionArea.setRows(5);
        jScrollPane2.setViewportView(descriptionArea);

        jPanel42.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Skills Required");
        jLabel30.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel30.setPreferredSize(new java.awt.Dimension(43, 26));
        jPanel42.add(jLabel30, java.awt.BorderLayout.PAGE_END);

        jPanel40.add(jPanel42, java.awt.BorderLayout.CENTER);

        jPanel37.add(jPanel40, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel37, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel36, java.awt.BorderLayout.CENTER);

        addNewInternship.add(jPanel35, java.awt.BorderLayout.CENTER);

        cardPanel.add(addNewInternship, "card5");

        deletedPanel.setLayout(new java.awt.BorderLayout());

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setPreferredSize(new java.awt.Dimension(944, 60));

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        deletedPanel.add(jPanel52, java.awt.BorderLayout.PAGE_START);

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setPreferredSize(new java.awt.Dimension(60, 454));

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        deletedPanel.add(jPanel53, java.awt.BorderLayout.LINE_START);

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));

        restoreButton.setBackground(new java.awt.Color(153, 153, 255));
        restoreButton.setText("Restore Internship");
        restoreButton.setPreferredSize(new java.awt.Dimension(109, 23));
        restoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addContainerGap(746, Short.MAX_VALUE)
                .addComponent(restoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(restoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        deletedPanel.add(jPanel54, java.awt.BorderLayout.PAGE_END);

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setPreferredSize(new java.awt.Dimension(60, 454));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        deletedPanel.add(jPanel55, java.awt.BorderLayout.LINE_END);

        deletedInternshipTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Title", "Company", "Type", "Duration", "Deadline"
            }
        ));
        jScrollPane3.setViewportView(deletedInternshipTable);

        deletedPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        cardPanel.add(deletedPanel, "card7");

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setRequestFocusEnabled(false);

        appliedInternshipsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Internship ID", "Title", "Company", "Deadline", "Total Applications"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        appliedInternshipsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appliedInternshipsTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(appliedInternshipsTable);
        if (appliedInternshipsTable.getColumnModel().getColumnCount() > 0) {
            appliedInternshipsTable.getColumnModel().getColumn(0).setResizable(false);
            appliedInternshipsTable.getColumnModel().getColumn(1).setResizable(false);
            appliedInternshipsTable.getColumnModel().getColumn(2).setResizable(false);
            appliedInternshipsTable.getColumnModel().getColumn(3).setResizable(false);
            appliedInternshipsTable.getColumnModel().getColumn(4).setResizable(false);
        }

        jSplitPane1.setLeftComponent(jScrollPane9);

        jPanel110.setLayout(new java.awt.BorderLayout());

        jPanel111.setBackground(new java.awt.Color(255, 255, 255));
        jPanel111.setPreferredSize(new java.awt.Dimension(429, 123));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Students Application");

        jLabel13.setText("Total Applications: ");

        applicationNo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel111Layout = new javax.swing.GroupLayout(jPanel111);
        jPanel111.setLayout(jPanel111Layout);
        jPanel111Layout.setHorizontalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel111Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel111Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(applicationNo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        jPanel111Layout.setVerticalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel111Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel111Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13))
                    .addGroup(jPanel111Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(applicationNo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel110.add(jPanel111, java.awt.BorderLayout.PAGE_START);

        studentApplicationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(studentApplicationTable);
        if (studentApplicationTable.getColumnModel().getColumnCount() > 0) {
            studentApplicationTable.getColumnModel().getColumn(0).setResizable(false);
            studentApplicationTable.getColumnModel().getColumn(1).setResizable(false);
            studentApplicationTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel110.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel110);

        javax.swing.GroupLayout applicationsViewLayout = new javax.swing.GroupLayout(applicationsView);
        applicationsView.setLayout(applicationsViewLayout);
        applicationsViewLayout.setHorizontalGroup(
            applicationsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(applicationsViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        applicationsViewLayout.setVerticalGroup(
            applicationsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, applicationsViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addContainerGap())
        );

        cardPanel.add(applicationsView, "card4");

        settingPanel.setLayout(new java.awt.BorderLayout());

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        settingPanel.add(jPanel45, java.awt.BorderLayout.PAGE_START);

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        settingPanel.add(jPanel46, java.awt.BorderLayout.PAGE_END);

        jPanel47.setLayout(new java.awt.BorderLayout());

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        jPanel47.add(jPanel48, java.awt.BorderLayout.LINE_START);

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        jPanel47.add(jPanel49, java.awt.BorderLayout.LINE_END);

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setLayout(new java.awt.BorderLayout());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setText("System Information");
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 50));
        jPanel50.add(jLabel31, java.awt.BorderLayout.PAGE_START);

        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea4.setRows(5);
        jTextArea4.setText("Admin Name : External Partnership Department\n\nRole: Admin \n\nEmail: externalpartnership@islingtoncollege.edu.np\n\nPassword:  admin123\n\n\nApplication Name: InternUp \n\nVersion: v1.0 (Academic Release)\n\nDescription:\n\nInternUp is an academic internship management system developed for educational purposes. \nThe system allows administrators to manage internship listings, monitor applications, and maintain \nstructured internship records within a controlled academic environment.");
        jScrollPane4.setViewportView(jTextArea4);

        jPanel50.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel47.add(jPanel50, java.awt.BorderLayout.CENTER);

        settingPanel.add(jPanel47, java.awt.BorderLayout.CENTER);

        cardPanel.add(settingPanel, "card6");

        Admin.add(cardPanel, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(170, 544));
        jPanel2.setLayout(new java.awt.CardLayout());

        AdminNav.setBackground(new java.awt.Color(255, 255, 255));

        adminNavigation.setBackground(new java.awt.Color(255, 255, 255));
        adminNavigation.setPreferredSize(new java.awt.Dimension(13, 185));
        adminNavigation.setLayout(new java.awt.GridLayout(6, 1, 0, 1));

        dashboardNav.setBackground(new java.awt.Color(153, 153, 255));
        dashboardNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        dashboardNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardNavMouseClicked(evt);
            }
        });

        jLabel2.setText("Dashboard");

        javax.swing.GroupLayout dashboardNavLayout = new javax.swing.GroupLayout(dashboardNav);
        dashboardNav.setLayout(dashboardNavLayout);
        dashboardNavLayout.setHorizontalGroup(
            dashboardNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardNavLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        dashboardNavLayout.setVerticalGroup(
            dashboardNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        adminNavigation.add(dashboardNav);

        manageNav.setBackground(new java.awt.Color(255, 255, 255));
        manageNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        manageNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageNavMouseClicked(evt);
            }
        });

        jLabel18.setText("Manage Internships");

        javax.swing.GroupLayout manageNavLayout = new javax.swing.GroupLayout(manageNav);
        manageNav.setLayout(manageNavLayout);
        manageNavLayout.setHorizontalGroup(
            manageNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageNavLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(22, 22, 22))
        );
        manageNavLayout.setVerticalGroup(
            manageNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation.add(manageNav);

        archiveNav.setBackground(new java.awt.Color(255, 255, 255));
        archiveNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        archiveNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                archiveNavMouseClicked(evt);
            }
        });

        jLabel32.setText("Archived Internships");

        javax.swing.GroupLayout archiveNavLayout = new javax.swing.GroupLayout(archiveNav);
        archiveNav.setLayout(archiveNavLayout);
        archiveNavLayout.setHorizontalGroup(
            archiveNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, archiveNavLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(15, 15, 15))
        );
        archiveNavLayout.setVerticalGroup(
            archiveNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, archiveNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation.add(archiveNav);

        applicationNav.setBackground(new java.awt.Color(255, 255, 255));
        applicationNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        applicationNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                applicationNavMouseClicked(evt);
            }
        });

        jLabel19.setText("Application");

        javax.swing.GroupLayout applicationNavLayout = new javax.swing.GroupLayout(applicationNav);
        applicationNav.setLayout(applicationNavLayout);
        applicationNavLayout.setHorizontalGroup(
            applicationNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(applicationNavLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel19)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        applicationNavLayout.setVerticalGroup(
            applicationNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(applicationNavLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel19)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        adminNavigation.add(applicationNav);

        settingsNav.setBackground(new java.awt.Color(255, 255, 255));
        settingsNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        settingsNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsNavMouseClicked(evt);
            }
        });

        jLabel21.setText("Settings");

        javax.swing.GroupLayout settingsNavLayout = new javax.swing.GroupLayout(settingsNav);
        settingsNav.setLayout(settingsNavLayout);
        settingsNavLayout.setHorizontalGroup(
            settingsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsNavLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel21)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        settingsNavLayout.setVerticalGroup(
            settingsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsNavLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        adminNavigation.add(settingsNav);

        logoutNav.setBackground(new java.awt.Color(255, 255, 255));
        logoutNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutNavMouseClicked(evt);
            }
        });

        jLabel20.setText("Settings");

        jLabel22.setText("Logout");

        javax.swing.GroupLayout logoutNavLayout = new javax.swing.GroupLayout(logoutNav);
        logoutNav.setLayout(logoutNavLayout);
        logoutNavLayout.setHorizontalGroup(
            logoutNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutNavLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(logoutNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        logoutNavLayout.setVerticalGroup(
            logoutNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutNavLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel22)
                .addGap(73, 73, 73)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        adminNavigation.add(logoutNav);

        javax.swing.GroupLayout AdminNavLayout = new javax.swing.GroupLayout(AdminNav);
        AdminNav.setLayout(AdminNavLayout);
        AdminNavLayout.setHorizontalGroup(
            AdminNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(AdminNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(adminNavigation, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
        );
        AdminNavLayout.setVerticalGroup(
            AdminNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 736, Short.MAX_VALUE)
            .addGroup(AdminNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AdminNavLayout.createSequentialGroup()
                    .addComponent(adminNavigation, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 473, Short.MAX_VALUE)))
        );

        jPanel2.add(AdminNav, "card4");

        Admin.add(jPanel2, java.awt.BorderLayout.LINE_START);

        parentcard.add(Admin, "card5");

        Student.setLayout(new java.awt.BorderLayout());

        jPanel61.setBackground(new java.awt.Color(255, 255, 255));
        jPanel61.setPreferredSize(new java.awt.Dimension(933, 80));

        ImageIcon icon12 = new ImageIcon("src/images/internupLogo.png");
        Image imgaa = icon12.getImage();
        Image scaled12 = imgaa.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
        jLabel37.setIcon(new ImageIcon(scaled12));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Hello");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel42.setText("Student");

        sSearchField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sSearchField.setPreferredSize(new java.awt.Dimension(64, 25));
        sSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sSearchFieldActionPerformed(evt);
            }
        });

        ImageIcon icon84 = new ImageIcon("src/images/user.png");
        Image img84 = icon84.getImage();
        Image scaled84 = img84.getScaledInstance(41, 34, Image.SCALE_SMOOTH);
        jLabel84.setIcon(new ImageIcon(scaled84));
        jLabel84.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        ImageIcon iconn1 = new ImageIcon("src/images/searchlogo.png");
        Image imgS1 = iconn1.getImage();
        Image scaledB1 = imgS1.getScaledInstance(42, 36, Image.SCALE_SMOOTH);
        btnSearch1.setIcon(new ImageIcon(scaledB1));
        btnSearch1.setBorder(null);
        btnSearch1.setBorderPainted(false);
        btnSearch1.setContentAreaFilled(false);
        btnSearch1.setFocusPainted(false);
        btnSearch1.setFocusable(false);
        btnSearch1.setRequestFocusEnabled(false);
        btnSearch1.setVerifyInputWhenFocusTarget(false);
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
                .addComponent(sSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                        .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(sSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21))))
        );

        Student.add(jPanel61, java.awt.BorderLayout.PAGE_START);

        jPanel65.setBackground(new java.awt.Color(255, 255, 255));
        jPanel65.setPreferredSize(new java.awt.Dimension(170, 544));
        jPanel65.setLayout(new java.awt.CardLayout());

        studentNav1.setBackground(new java.awt.Color(255, 255, 255));
        studentNav1.setAutoscrolls(true);

        adminNavigation3.setBackground(new java.awt.Color(255, 255, 255));
        adminNavigation3.setPreferredSize(new java.awt.Dimension(13, 185));
        adminNavigation3.setLayout(new java.awt.GridLayout(5, 1, 0, 1));

        studentDashboardNav.setBackground(new java.awt.Color(153, 153, 255));
        studentDashboardNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        studentDashboardNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentDashboardNavMouseClicked(evt);
            }
        });

        jLabel50.setText("Dashboard");

        javax.swing.GroupLayout studentDashboardNavLayout = new javax.swing.GroupLayout(studentDashboardNav);
        studentDashboardNav.setLayout(studentDashboardNavLayout);
        studentDashboardNavLayout.setHorizontalGroup(
            studentDashboardNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentDashboardNavLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        studentDashboardNavLayout.setVerticalGroup(
            studentDashboardNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        adminNavigation3.add(studentDashboardNav);

        viewInternship.setBackground(new java.awt.Color(255, 255, 255));
        viewInternship.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        viewInternship.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInternshipMouseClicked(evt);
            }
        });

        jLabel51.setText("View Internships");

        javax.swing.GroupLayout viewInternshipLayout = new javax.swing.GroupLayout(viewInternship);
        viewInternship.setLayout(viewInternshipLayout);
        viewInternshipLayout.setHorizontalGroup(
            viewInternshipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInternshipLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addGap(35, 35, 35))
        );
        viewInternshipLayout.setVerticalGroup(
            viewInternshipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInternshipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation3.add(viewInternship);

        studentSettings.setBackground(new java.awt.Color(255, 255, 255));
        studentSettings.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        studentSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentSettingsMouseClicked(evt);
            }
        });

        jLabel53.setText("Settings");

        javax.swing.GroupLayout studentSettingsLayout = new javax.swing.GroupLayout(studentSettings);
        studentSettings.setLayout(studentSettingsLayout);
        studentSettingsLayout.setHorizontalGroup(
            studentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentSettingsLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel53)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        studentSettingsLayout.setVerticalGroup(
            studentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentSettingsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel53)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        adminNavigation3.add(studentSettings);

        jPanel76.setBackground(new java.awt.Color(255, 255, 255));
        jPanel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel76MouseClicked(evt);
            }
        });

        jLabel54.setText("Settings");

        jLabel55.setText("Logout");

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel54))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel55)
                .addGap(73, 73, 73)
                .addComponent(jLabel54)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        adminNavigation3.add(jPanel76);

        javax.swing.GroupLayout studentNav1Layout = new javax.swing.GroupLayout(studentNav1);
        studentNav1.setLayout(studentNav1Layout);
        studentNav1Layout.setHorizontalGroup(
            studentNav1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminNavigation3, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        studentNav1Layout.setVerticalGroup(
            studentNav1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentNav1Layout.createSequentialGroup()
                .addComponent(adminNavigation3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 506, Short.MAX_VALUE))
        );

        jPanel65.add(studentNav1, "card3");

        Student.add(jPanel65, java.awt.BorderLayout.LINE_START);

        cardPanel1.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel1.setLayout(new java.awt.CardLayout());

        dashboardPanel1.setBackground(new java.awt.Color(255, 255, 204));
        dashboardPanel1.setLayout(new java.awt.BorderLayout());

        jPanel66.setBackground(new java.awt.Color(255, 255, 255));
        jPanel66.setPreferredSize(new java.awt.Dimension(763, 150));
        jPanel66.setLayout(new java.awt.BorderLayout());

        jPanel67.setBackground(new java.awt.Color(255, 255, 255));
        jPanel67.setPreferredSize(new java.awt.Dimension(20, 100));

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel66.add(jPanel67, java.awt.BorderLayout.EAST);

        jPanel68.setLayout(new java.awt.BorderLayout());

        jPanel69.setBackground(new java.awt.Color(255, 255, 255));
        jPanel69.setPreferredSize(new java.awt.Dimension(20, 100));

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel68.add(jPanel69, java.awt.BorderLayout.LINE_START);

        gridPanel1.setBackground(new java.awt.Color(255, 255, 255));
        gridPanel1.setLayout(new java.awt.GridLayout(1, 4, 20, 0));

        jPanel70.setBackground(new java.awt.Color(255, 255, 255));
        jPanel70.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel70.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ING Companies");
        jPanel70.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        noOfStudent1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        noOfStudent1.setForeground(new java.awt.Color(255, 255, 255));
        noOfStudent1.setText("42+");
        jPanel70.add(noOfStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 70, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel35.setText("jLabel4");
        jPanel70.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 160));

        gridPanel1.add(jPanel70);

        jPanel71.setBackground(new java.awt.Color(255, 255, 255));
        jPanel71.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel71.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Partner Companies");
        jPanel71.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("200+");
        jPanel71.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel45.setText("jLabel4");
        jPanel71.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));

        gridPanel1.add(jPanel71);

        jPanel77.setBackground(new java.awt.Color(255, 255, 255));
        jPanel77.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel77.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Active Internship ");
        jPanel77.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        noOfInternship1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        noOfInternship1.setForeground(new java.awt.Color(255, 255, 255));
        noOfInternship1.setText("8");
        jPanel77.add(noOfInternship1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 50, -1));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jPanel77.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 150));

        gridPanel1.add(jPanel77);

        jPanel78.setBackground(new java.awt.Color(255, 255, 255));
        jPanel78.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel78.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Internship Applied");
        jPanel78.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        indiviudalNoOfApplications.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        indiviudalNoOfApplications.setForeground(new java.awt.Color(255, 255, 255));
        indiviudalNoOfApplications.setText("0");
        jPanel78.add(indiviudalNoOfApplications, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 50, 50));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background1.png"))); // NOI18N
        jLabel49.setText("jLabel4");
        jPanel78.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 151));

        gridPanel1.add(jPanel78);

        jPanel68.add(gridPanel1, java.awt.BorderLayout.CENTER);

        jPanel66.add(jPanel68, java.awt.BorderLayout.CENTER);

        dashboardPanel1.add(jPanel66, java.awt.BorderLayout.PAGE_START);

        jPanel79.setLayout(new java.awt.BorderLayout());

        jPanel80.setBackground(new java.awt.Color(153, 153, 255));
        jPanel80.setPreferredSize(new java.awt.Dimension(300, 392));
        jPanel80.setLayout(new java.awt.BorderLayout());

        jPanel81.setBackground(new java.awt.Color(255, 255, 255));
        jPanel81.setPreferredSize(new java.awt.Dimension(300, 112));

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jPanel80.add(jPanel81, java.awt.BorderLayout.PAGE_END);

        jPanel82.setBackground(new java.awt.Color(255, 255, 255));
        jPanel82.setPreferredSize(new java.awt.Dimension(300, 494));
        jPanel82.setVerifyInputWhenFocusTarget(false);

        ImageIcon icon82 = new ImageIcon("src/images/companies.png");
        Image imgge = icon82.getImage();
        Image scaledCom82 = imgge.getScaledInstance(266, 479, Image.SCALE_SMOOTH);
        jLabel82.setIcon(new ImageIcon(scaledCom82));
        jLabel82.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel82Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel82Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel80.add(jPanel82, java.awt.BorderLayout.CENTER);

        jPanel79.add(jPanel80, java.awt.BorderLayout.LINE_END);

        jPanel83.setBackground(new java.awt.Color(255, 255, 255));
        jPanel83.setPreferredSize(new java.awt.Dimension(704, 464));
        jPanel83.setLayout(new java.awt.BorderLayout());

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setText("Recent Intenships");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel52)
                .addContainerGap(469, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addGap(14, 14, 14))
        );

        jPanel83.add(jPanel84, java.awt.BorderLayout.PAGE_START);

        internshipPreviewPanel1.setBackground(new java.awt.Color(255, 255, 255));
        internshipPreviewPanel1.setPreferredSize(new java.awt.Dimension(714, 341));
        internshipPreviewPanel1.setLayout(new java.awt.BorderLayout());

        jPanel118.setBackground(new java.awt.Color(255, 255, 255));
        jPanel118.setPreferredSize(new java.awt.Dimension(40, 341));

        javax.swing.GroupLayout jPanel118Layout = new javax.swing.GroupLayout(jPanel118);
        jPanel118.setLayout(jPanel118Layout);
        jPanel118Layout.setHorizontalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel118Layout.setVerticalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        internshipPreviewPanel1.add(jPanel118, java.awt.BorderLayout.LINE_START);

        cardContainerPanel1.setBackground(new java.awt.Color(255, 255, 255));
        cardContainerPanel1.setLayout(new java.awt.GridLayout(1, 2, 15, 15));
        internshipPreviewPanel1.add(cardContainerPanel1, java.awt.BorderLayout.CENTER);

        jPanel83.add(internshipPreviewPanel1, java.awt.BorderLayout.CENTER);

        jPanel119.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel119Layout = new javax.swing.GroupLayout(jPanel119);
        jPanel119.setLayout(jPanel119Layout);
        jPanel119Layout.setHorizontalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );
        jPanel119Layout.setVerticalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jPanel83.add(jPanel119, java.awt.BorderLayout.PAGE_END);

        jPanel79.add(jPanel83, java.awt.BorderLayout.CENTER);

        dashboardPanel1.add(jPanel79, java.awt.BorderLayout.CENTER);

        cardPanel1.add(dashboardPanel1, "card67");

        applicationsView1.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(60, 514));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        applicationsView1.add(jPanel7, java.awt.BorderLayout.LINE_START);

        jPanel62.setBackground(new java.awt.Color(255, 255, 255));
        jPanel62.setPreferredSize(new java.awt.Dimension(60, 514));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        applicationsView1.add(jPanel62, java.awt.BorderLayout.LINE_END);

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel69.setText("All Internships");

        sCompanyButton.setText("Sort By Company");
        sCompanyButton.setPreferredSize(new java.awt.Dimension(150, 30));
        sCompanyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sCompanyButtonMouseClicked(evt);
            }
        });
        sCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sCompanyButtonActionPerformed(evt);
            }
        });

        sDeadlineButton.setText("Sort By Deadline");
        sDeadlineButton.setPreferredSize(new java.awt.Dimension(150, 30));
        sDeadlineButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sDeadlineButtonMouseClicked(evt);
            }
        });
        sDeadlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sDeadlineButtonActionPerformed(evt);
            }
        });

        sTypeButton.setText("Sort by Type");
        sTypeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sTypeButtonMouseClicked(evt);
            }
        });
        sTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sTypeButtonActionPerformed(evt);
            }
        });

        sTitleButton.setText("Sort By Title");
        sTitleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sTitleButtonMouseClicked(evt);
            }
        });
        sTitleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sTitleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addComponent(sTitleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sDeadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel69))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sDeadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTitleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        applicationsView1.add(jPanel63, java.awt.BorderLayout.PAGE_START);

        jPanel72.setBackground(new java.awt.Color(255, 255, 255));
        jPanel72.setPreferredSize(new java.awt.Dimension(946, 80));

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        applicationsView1.add(jPanel72, java.awt.BorderLayout.PAGE_END);

        jPanel85.setLayout(new java.awt.BorderLayout());

        studentCardContainerPanel.setLayout(new java.awt.GridBagLayout());
        internshipScrollPane.setViewportView(studentCardContainerPanel);

        jPanel85.add(internshipScrollPane, java.awt.BorderLayout.CENTER);

        applicationsView1.add(jPanel85, java.awt.BorderLayout.CENTER);

        cardPanel1.add(applicationsView1, "card4");

        studentsettingPanel1.setLayout(new java.awt.BorderLayout());

        jPanel112.setBackground(new java.awt.Color(255, 255, 255));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel70.setText("My Profile");
        jLabel70.setPreferredSize(new java.awt.Dimension(55, 50));

        javax.swing.GroupLayout jPanel112Layout = new javax.swing.GroupLayout(jPanel112);
        jPanel112.setLayout(jPanel112Layout);
        jPanel112Layout.setHorizontalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel112Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel112Layout.setVerticalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel112Layout.createSequentialGroup()
                .addGap(0, 50, Short.MAX_VALUE)
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        studentsettingPanel1.add(jPanel112, java.awt.BorderLayout.PAGE_START);

        jPanel113.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel113Layout = new javax.swing.GroupLayout(jPanel113);
        jPanel113.setLayout(jPanel113Layout);
        jPanel113Layout.setHorizontalGroup(
            jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel113Layout.setVerticalGroup(
            jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        studentsettingPanel1.add(jPanel113, java.awt.BorderLayout.PAGE_END);

        jPanel114.setLayout(new java.awt.BorderLayout());

        jPanel115.setBackground(new java.awt.Color(255, 255, 255));
        jPanel115.setPreferredSize(new java.awt.Dimension(40, 536));

        javax.swing.GroupLayout jPanel115Layout = new javax.swing.GroupLayout(jPanel115);
        jPanel115.setLayout(jPanel115Layout);
        jPanel115Layout.setHorizontalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel115Layout.setVerticalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        jPanel114.add(jPanel115, java.awt.BorderLayout.LINE_START);

        jPanel116.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel116Layout = new javax.swing.GroupLayout(jPanel116);
        jPanel116.setLayout(jPanel116Layout);
        jPanel116Layout.setHorizontalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel116Layout.setVerticalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );

        jPanel114.add(jPanel116, java.awt.BorderLayout.LINE_END);

        jPanel117.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setText("Full Name");

        jLabel57.setText("College ID");

        jLabel58.setText("Email");

        jLabel59.setText("This is your primary email address and will ");

        jLabel60.setText("be used to send notification emails.");

        jTextField8.setText("Islington College Kathmandu");

        jLabel61.setText("College");

        jLabel64.setText("Password");

        jLabel65.setText("Location");

        jTextField10.setText("Kamalpokhari,Kathmandu");

        ImageIcon icon66 = new ImageIcon("src/images/user.png");
        Image imga66 = icon66.getImage();
        Image scaled66 = imga66.getScaledInstance(201, 191, Image.SCALE_SMOOTH);
        jLabel66.setIcon(new ImageIcon(scaled66));

        javax.swing.GroupLayout jPanel117Layout = new javax.swing.GroupLayout(jPanel117);
        jPanel117.setLayout(jPanel117Layout);
        jPanel117Layout.setHorizontalGroup(
            jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel117Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField8)
                        .addComponent(jLabel61)
                        .addComponent(settingsPass, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel64))
                    .addComponent(jLabel60)
                    .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(settingsEmail)
                        .addComponent(jLabel58)
                        .addComponent(settingCollegeID)
                        .addComponent(jLabel57)
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsName, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel117Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(198, 198, 198))
        );
        jPanel117Layout.setVerticalGroup(
            jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel117Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel117Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(settingCollegeID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(settingsEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel59)))
                .addGroup(jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel117Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(settingsPass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel117Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(settingsName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel114.add(jPanel117, java.awt.BorderLayout.CENTER);

        studentsettingPanel1.add(jPanel114, java.awt.BorderLayout.CENTER);

        cardPanel1.add(studentsettingPanel1, "card6");

        addNewInternship1.setBackground(new java.awt.Color(255, 255, 255));
        addNewInternship1.setLayout(new java.awt.BorderLayout());

        jPanel86.setBackground(new java.awt.Color(255, 255, 255));
        jPanel86.setPreferredSize(new java.awt.Dimension(817, 40));

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        addNewInternship1.add(jPanel86, java.awt.BorderLayout.PAGE_START);

        jPanel87.setBackground(new java.awt.Color(255, 255, 255));
        jPanel87.setLayout(new java.awt.BorderLayout());

        jPanel88.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel88Layout = new javax.swing.GroupLayout(jPanel88);
        jPanel88.setLayout(jPanel88Layout);
        jPanel88Layout.setHorizontalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel88Layout.setVerticalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
        );

        jPanel87.add(jPanel88, java.awt.BorderLayout.WEST);

        jPanel89.setLayout(new java.awt.BorderLayout());

        jPanel90.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel90Layout = new javax.swing.GroupLayout(jPanel90);
        jPanel90.setLayout(jPanel90Layout);
        jPanel90Layout.setHorizontalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel90Layout.setVerticalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
        );

        jPanel89.add(jPanel90, java.awt.BorderLayout.LINE_END);

        jPanel91.setLayout(new java.awt.BorderLayout());

        jPanel92.setBackground(new java.awt.Color(255, 255, 255));
        jPanel92.setPreferredSize(new java.awt.Dimension(617, 210));
        jPanel92.setLayout(new java.awt.GridLayout(6, 2, 50, 5));

        jLabel71.setText("Title");
        jLabel71.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel71.setInheritsPopupMenu(false);
        jPanel92.add(jLabel71);

        jLabel72.setText("Company");
        jLabel72.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel92.add(jLabel72);

        titleFieldS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        titleFieldS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleFieldSActionPerformed(evt);
            }
        });
        jPanel92.add(titleFieldS);

        companyFieldS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel92.add(companyFieldS);

        jLabel73.setText("Deadline");
        jLabel73.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel92.add(jLabel73);

        jLabel74.setText("Salary");
        jLabel74.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel92.add(jLabel74);

        jPanel93.setLayout(new java.awt.GridLayout(1, 3));

        dayCombo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dayCombo1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        dayCombo1.setPreferredSize(new java.awt.Dimension(82, 22));
        jPanel93.add(dayCombo1);

        monthComboS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        jPanel93.add(monthComboS);

        yearComboS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year", "2025", "2026", "2027" }));
        yearComboS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboSActionPerformed(evt);
            }
        });
        jPanel93.add(yearComboS);

        jPanel92.add(jPanel93);

        salaryFieldS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel92.add(salaryFieldS);

        jLabel75.setText("Internship Type");
        jLabel75.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel92.add(jLabel75);

        jLabel76.setText("Duration");
        jLabel76.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel92.add(jLabel76);

        typeComboS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Remote", "On Site", "International" }));
        jPanel92.add(typeComboS);

        durationComboS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Month", "3 Months", "6 Months", "12 Months ", "36 Months" }));
        durationComboS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                durationComboSActionPerformed(evt);
            }
        });
        jPanel92.add(durationComboS);

        jPanel91.add(jPanel92, java.awt.BorderLayout.PAGE_START);

        jPanel94.setBackground(new java.awt.Color(255, 255, 255));
        jPanel94.setPreferredSize(new java.awt.Dimension(617, 50));

        jPanel95.setBackground(new java.awt.Color(255, 255, 255));
        jPanel95.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jButton17.setText("Exit");
        jButton17.setPreferredSize(new java.awt.Dimension(120, 30));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel95.add(jButton17);

        applyButton.setBackground(new java.awt.Color(153, 153, 255));
        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });
        jPanel95.add(applyButton);

        javax.swing.GroupLayout jPanel94Layout = new javax.swing.GroupLayout(jPanel94);
        jPanel94.setLayout(jPanel94Layout);
        jPanel94Layout.setHorizontalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addContainerGap(466, Short.MAX_VALUE)
                .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel94Layout.setVerticalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel94Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel91.add(jPanel94, java.awt.BorderLayout.PAGE_END);

        jPanel96.setBackground(new java.awt.Color(255, 255, 255));
        jPanel96.setLayout(new java.awt.BorderLayout());

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Description");
        jLabel77.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel77.setPreferredSize(new java.awt.Dimension(43, 50));
        jPanel96.add(jLabel77, java.awt.BorderLayout.PAGE_START);

        skillAreaS.setColumns(20);
        skillAreaS.setRows(5);
        jScrollPane6.setViewportView(skillAreaS);

        jPanel96.add(jScrollPane6, java.awt.BorderLayout.PAGE_END);

        jPanel97.setBackground(new java.awt.Color(255, 255, 255));
        jPanel97.setLayout(new java.awt.BorderLayout());

        descriptionAreaS.setColumns(20);
        descriptionAreaS.setRows(5);
        jScrollPane8.setViewportView(descriptionAreaS);

        jPanel97.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Skills Required");
        jLabel78.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel78.setPreferredSize(new java.awt.Dimension(43, 26));
        jPanel97.add(jLabel78, java.awt.BorderLayout.PAGE_END);

        jPanel96.add(jPanel97, java.awt.BorderLayout.CENTER);

        jPanel91.add(jPanel96, java.awt.BorderLayout.CENTER);

        jPanel89.add(jPanel91, java.awt.BorderLayout.CENTER);

        jPanel87.add(jPanel89, java.awt.BorderLayout.CENTER);

        addNewInternship1.add(jPanel87, java.awt.BorderLayout.CENTER);

        cardPanel1.add(addNewInternship1, "card5");

        Student.add(cardPanel1, java.awt.BorderLayout.CENTER);

        parentcard.add(Student, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentcard, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentcard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardNavMouseClicked
        loadInternshipCards();
        CardLayout c2 = (CardLayout)(cardPanel.getLayout());
        c2.show(cardPanel, "dashboard");   
        setNumber();
        resetNavColors();
        dashboardNav.setBackground(activeColor);
    }//GEN-LAST:event_dashboardNavMouseClicked

    private void manageNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageNavMouseClicked
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "card3"); 
        resetNavColors();
        manageNav.setBackground(activeColor);
        resetSortButtonColors();
        InternshipController.sortById();
        loadInternshipsToTable();
        
    }//GEN-LAST:event_manageNavMouseClicked

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        CardLayout c3 = (CardLayout)(cardPanel.getLayout());
        c3.show(cardPanel, "card5");
        isUpdateMode = false;
        editingIndex = -1;
        add_updateButton.setText("Post Internship");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void durationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_durationButtonActionPerformed
        InternshipController.sortByDuration() ;
        loadInternshipsToTable();
    }//GEN-LAST:event_durationButtonActionPerformed

    private void viewMode(){
        titleField.setEnabled(true);titleField.setEditable(false);titleField.setFocusable(false);titleField.setBackground(Color.WHITE);titleField.setForeground(Color.BLACK);
        companyField.setEnabled(true);companyField.setEditable(false);companyField.setFocusable(false);companyField.setBackground(Color.WHITE);companyField.setForeground(Color.BLACK);
        skillArea.setEnabled(true);skillArea.setEditable(false);skillArea.setFocusable(false);skillArea.setBackground(Color.WHITE);skillArea.setForeground(Color.BLACK);
        salaryField.setEnabled(true);salaryField.setEditable(false);salaryField.setFocusable(false);salaryField.setBackground(Color.WHITE);salaryField.setForeground(Color.BLACK);
        descriptionArea.setEnabled(true);descriptionArea.setEditable(false);descriptionArea.setFocusable(false);descriptionArea.setBackground(Color.WHITE);descriptionArea.setForeground(Color.BLACK);
        typeCombo.setEnabled(false);typeCombo.setEditable(false);typeCombo.setFocusable(false);typeCombo.setBackground(Color.WHITE);typeCombo.setForeground(Color.BLACK);
        durationCombo.setEnabled(false);durationCombo.setEditable(false);durationCombo.setFocusable(false);durationCombo.setBackground(Color.WHITE);durationCombo.setForeground(Color.BLACK);
        yearCombo.setEnabled(false);yearCombo.setEditable(false);yearCombo.setFocusable(false);yearCombo.setBackground(Color.WHITE);yearCombo.setForeground(Color.BLACK);
        monthCombo.setEnabled(false);monthCombo.setEditable(false);monthCombo.setFocusable(false);monthCombo.setBackground(Color.WHITE);monthCombo.setForeground(Color.BLACK);
        dayCombo.setEnabled(false); dayCombo.setEditable(false);dayCombo.setFocusable(false);dayCombo.setBackground(Color.WHITE);dayCombo.setForeground(Color.BLACK);
    }
    private void editMode(){
        titleField.setEditable(true);titleField.setFocusable(true);
        companyField.setEditable(true);companyField.setFocusable(true);
        salaryField.setEditable(true);salaryField.setFocusable(true);
        descriptionArea.setEditable(true);descriptionArea.setFocusable(true);
        skillArea.setEditable(true);skillArea.setFocusable(true);
        typeCombo.setEnabled(true);typeCombo.setFocusable(true);typeCombo.setBackground(Color.WHITE);typeCombo.setForeground(Color.BLACK);
        durationCombo.setEnabled(true);durationCombo.setFocusable(true);durationCombo.setBackground(Color.WHITE);durationCombo.setForeground(Color.BLACK);
        yearCombo.setEnabled(true);yearCombo.setFocusable(true);yearCombo.setBackground(Color.WHITE);yearCombo.setForeground(Color.BLACK);
        monthCombo.setEnabled(true);monthCombo.setFocusable(true);monthCombo.setBackground(Color.WHITE);monthCombo.setForeground(Color.BLACK);
        dayCombo.setEnabled(true); dayCombo.setFocusable(true);dayCombo.setBackground(Color.WHITE);dayCombo.setForeground(Color.BLACK);

    }
    private void viewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDetailsActionPerformed
        int selectedRow = internshipTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an internship to view.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }else{
            isViewMode = true;
            isUpdateMode = false;

            CardLayout c13 = (CardLayout)(cardPanel.getLayout());
            c13.show(cardPanel, "card5");
            add_updateButton.setText("Exit");
            cancelButton.setVisible(false);
            editingIndex = selectedRow;

            Internship i = InternshipController.internshipList.get(selectedRow);

            titleField.setText(i.getTitle());
            companyField.setText(i.getCompany());
            salaryField.setText(String.valueOf(i.getSalary()));            
            descriptionArea.setText(i.getDescription());
            skillArea.setText(i.getRequirement());
            typeCombo.setSelectedItem(i.getType());
            durationCombo.setSelectedItem(i.getDuration());
            String[] date = i.getDeadline().split("-");
            yearCombo.setSelectedItem(date[0]);
            monthCombo.setSelectedItem(date[1]);
            dayCombo1.setSelectedItem(date[2]);
            
            viewMode();              
        }
    }//GEN-LAST:event_viewDetailsActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        int selectedRow = internshipTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an internship to update.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        isUpdateMode = true;
        editingIndex = selectedRow;

        Internship i = InternshipController.internshipList.get(selectedRow);

        titleField.setText(i.getTitle());
        companyField.setText(i.getCompany());
        salaryField.setText(String.valueOf(i.getSalary()));
        descriptionArea.setText(i.getDescription());
        skillArea.setText(i.getRequirement());
        typeCombo.setSelectedItem(i.getType());
        durationCombo.setSelectedItem(i.getDuration());
        String[] date = i.getDeadline().split("-");
        yearCombo.setSelectedItem(date[0]);
        monthCombo.setSelectedItem(date[1]);
        dayCombo.setSelectedItem(date[2]);
        editMode();
        CardLayout c13 = (CardLayout)(cardPanel.getLayout());
        c13.show(cardPanel, "card5");
        add_updateButton.setText("Update");

    }//GEN-LAST:event_jButton9ActionPerformed

    private void logoutNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutNavMouseClicked
        CardLayout c14 = (CardLayout)(parentcard.getLayout());
        c14.show(parentcard, "card4"); 
        resetNavColors();
    }//GEN-LAST:event_logoutNavMouseClicked

    private void titleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        int userChoice = JOptionPane.showConfirmDialog(this,"Are you sure you want to cancel?", 
        "Confirm Cancel",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (userChoice == JOptionPane.YES_OPTION) {
            clearInternshipForm(); 
            CardLayout cl9 = (CardLayout)(cardPanel.getLayout());
            cl9.show(cardPanel, "card3");
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void durationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_durationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_durationComboActionPerformed

    private void settingsNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsNavMouseClicked
        CardLayout c4 = (CardLayout)(cardPanel.getLayout());
        c4.show(cardPanel, "card6");  
        resetNavColors();
        settingsNav.setBackground(activeColor);
    }//GEN-LAST:event_settingsNavMouseClicked

    private void restoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreButtonActionPerformed
        if (InternshipController.top == -1) { // stack empty
        JOptionPane.showMessageDialog(this, 
            "No deleted internships to restore.",
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }

        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to restore the last deleted internship?",
            "Confirm Restore", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = InternshipController.restoreInternship(); // POP from stack
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Internship restored successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadInternshipsToTable();
                InternshipController.refreshQueue();
                loadDeletedInternshipsToTable(); // if you have a table showing stack contents
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to restore internship.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_restoreButtonActionPerformed

    private void archiveNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_archiveNavMouseClicked
        CardLayout c7 = (CardLayout)(cardPanel.getLayout());
        c7.show(cardPanel, "card7"); 
        resetNavColors();
        archiveNav.setBackground(activeColor);
    }//GEN-LAST:event_archiveNavMouseClicked

    private void applicationNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applicationNavMouseClicked
        CardLayout c5 = (CardLayout)(cardPanel.getLayout());
        c5.show(cardPanel, "card4"); 
        resetNavColors();
        applicationNav.setBackground(activeColor);
    }//GEN-LAST:event_applicationNavMouseClicked

    private void sSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sSearchFieldActionPerformed

    private void studentDashboardNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentDashboardNavMouseClicked
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card67");  
        setNumber();
        resetNavColors();
        studentDashboardNav.setBackground(activeColor);
    }//GEN-LAST:event_studentDashboardNavMouseClicked

    private void viewInternshipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInternshipMouseClicked
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card4");         
        resetSortButtonColors();
        loadStudentInternshipCards();
        resetNavColors();
        viewInternship.setBackground(activeColor);              
    }//GEN-LAST:event_viewInternshipMouseClicked

    private void studentSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentSettingsMouseClicked
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card6");  
        setStudentDetails();
        resetNavColors();
        studentSettings.setBackground(activeColor); 
    }//GEN-LAST:event_studentSettingsMouseClicked

    private void jPanel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel76MouseClicked
        CardLayout c14 = (CardLayout)(parentcard.getLayout());
        c14.show(parentcard, "card4"); 
        resetNavColors();
    }//GEN-LAST:event_jPanel76MouseClicked

    private void studentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentButtonActionPerformed
        
        CardLayout c22 = (CardLayout)(cardPanel2.getLayout());
        c22.show(cardPanel2, "student");
    }//GEN-LAST:event_studentButtonActionPerformed

    private void adminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminButtonActionPerformed
        
        CardLayout c2 = (CardLayout)(cardPanel2.getLayout());
        c2.show(cardPanel2, "AdminPanel");
    }//GEN-LAST:event_adminButtonActionPerformed
    
    private void clearInternshipForm() {
        titleField.setText("");
        companyField.setText("");
        salaryField.setText("");
        descriptionArea.setText("");
        skillArea.setText("");
        typeCombo.setSelectedIndex(0);
        durationCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        dayCombo.setSelectedIndex(0);
    }
    private void add_updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_updateButtonActionPerformed
        if (isViewMode) {
            isViewMode = false;
            clearInternshipForm();
            add_updateButton.setText("Post Internship");

            CardLayout cl = (CardLayout)(cardPanel.getLayout());
            cl.show(cardPanel, "card3"); 
            return;
            }
        
        String title = titleField.getText().trim();
        String company = companyField.getText().trim();
        String year = yearCombo.getSelectedItem().toString();
        String month = monthCombo.getSelectedItem().toString();
        String day = dayCombo.getSelectedItem().toString();
        String salaryText = salaryField.getText().trim();
        String type = typeCombo.getSelectedItem().toString();
        String duration = durationCombo.getSelectedItem().toString();
        String description = descriptionArea.getText().trim();
        String requirement = skillArea.getText().trim();
        String deadline;
        
        if (title.isEmpty() || company.isEmpty() || salaryText.isEmpty() || description.isEmpty() || requirement.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int salary;
        try {
            salary = Integer.parseInt(salaryText);
       } 
        catch (NumberFormatException e){

            JOptionPane.showMessageDialog(this, "Salary must be a number.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (year.equals("Year") || month.equals("Month") || day.equals("Day")) 
        {
            JOptionPane.showMessageDialog(this, "Please select a valid date.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            
            deadline = year + "-" + month + "-" + day;

            
            if (isUpdateMode) 
            {
                int userChoice2 = JOptionPane.showConfirmDialog(this,"Are you sure you want to update Internship.", 
                                                                    "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userChoice2 == JOptionPane.YES_OPTION) 
                    {
                        if (InternshipController.isSameAsOriginal( editingIndex, title, company, deadline,salary, type, duration, description, requirement)) 
                        {
                            JOptionPane.showMessageDialog(this,"You must change at least one field to update.","Error",JOptionPane.ERROR_MESSAGE);
                            return;
                        }else if (InternshipController.isDuplicateForUpdate(editingIndex, title, company, deadline,salary, type, duration, description, requirement)) 
                        {
                            JOptionPane.showMessageDialog(this,"An internship with the same details already exists.","Error",JOptionPane.ERROR_MESSAGE);
                            return;
                        } else{
                            InternshipController.updateInternship(editingIndex, title, company,deadline, salary, type, duration, description, requirement);
                            loadInternshipsToTable();
                            clearInternshipForm();
                            JOptionPane.showMessageDialog(this, "Internship updated successfully.","Success", JOptionPane.INFORMATION_MESSAGE);

                            isUpdateMode = false;
                            editingIndex = -1;
                            add_updateButton.setText("Post Internship");
                            CardLayout cl = (CardLayout)(cardPanel.getLayout());
                            cl.show(cardPanel, "card3");    
                            return;
                        }
                    }
            }
            else{
                int userChoice1 = JOptionPane.showConfirmDialog(this,"Are you sure you want to add post new Internship.", 
                                                                    "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (userChoice1 == JOptionPane.YES_OPTION) 
                {                    
                    InternshipController.addInternship(title,company,deadline, salary,type, duration, description,requirement);
                    loadInternshipsToTable();
                    clearInternshipForm();
                    Internship newInternship = new Internship(title, company, deadline, salary, type, duration, description, requirement);
                    InternshipController.enqueue(newInternship);
                    JOptionPane.showMessageDialog(this, "Internship added successfully.","Success", JOptionPane.INFORMATION_MESSAGE);
                    CardLayout cl = (CardLayout)(cardPanel.getLayout());
                    cl.show(cardPanel, "card3"); 
                }
            }
            
        }                          
    }//GEN-LAST:event_add_updateButtonActionPerformed

    private void yearComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int selectedRow = internshipTable.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Please select an internship to delete.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this internship?",
                                                        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = InternshipController.deleteInternship(selectedRow);
            if (success) {
                JOptionPane.showMessageDialog(this, "Internship deleted successfully.","Success", JOptionPane.INFORMATION_MESSAGE);
                loadInternshipsToTable();
                InternshipController.refreshQueue();                
                loadDeletedInternshipsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete internship.","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void titleFieldSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleFieldSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleFieldSActionPerformed

    private void yearComboSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboSActionPerformed

    private void durationComboSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_durationComboSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_durationComboSActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card4"); 
    }//GEN-LAST:event_jButton17ActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        String studentId = StudentController.getCurrentStudentId();
        if (studentId == null) {
            JOptionPane.showMessageDialog(this,"Please login before applying for an internship.","Not Logged In",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedInternship == null) {
            JOptionPane.showMessageDialog(this,"No internship selected.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        String internshipId = selectedInternship.getId(); 

        boolean applied = ApplicationController.applyInternship(studentId, internshipId);

        if (applied) 
        {
            JOptionPane.showMessageDialog(this,"Application submitted successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
            loadAppliedInternships();
        } else 
        {
            JOptionPane.showMessageDialog(this,"You have already applied for this internship.","Error",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_applyButtonActionPerformed

    private void sTitleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sTitleButtonActionPerformed
        InternshipController.sortByTitle();
        loadStudentInternshipCards();

    }//GEN-LAST:event_sTitleButtonActionPerformed

    private void titleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleButtonMouseClicked
        resetSortButtonColors();
        titleButton.setBackground(activeColor);
    }//GEN-LAST:event_titleButtonMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        CardLayout c13 = (CardLayout)(parentcard.getLayout());
        c13.show(parentcard, "card5");
        resetNavColors();
        dashboardNav.setBackground(activeColor);
        setNumber();
        

        /*
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()); // for JPasswordField

        AdminController adminController = new AdminController();
        String result = adminController.login(username, password);

        if (result == null) {
            JOptionPane.showMessageDialog(this, "Admin Login successful!", "Success",JOptionPane.INFORMATION_MESSAGE);
        clearloginFields();    
        CardLayout c13 = (CardLayout)(parentcard.getLayout());
            c13.show(parentcard, "card5");
        } else {
            JOptionPane.showMessageDialog(this, result, "Login Error", JOptionPane.ERROR_MESSAGE);
        }*/
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void collegeIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collegeIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_collegeIDFieldActionPerformed

    private void passWordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passWordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passWordFieldActionPerformed

    private void studentLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentLoginActionPerformed
        String id = collegeIDField.getText().trim();
        char[] passwordChars = passWordField.getPassword();
        String password = new String(passwordChars);
        if(id.isEmpty( )){
            JOptionPane.showMessageDialog(this,"Please enter college ID!!","Login Failed",JOptionPane.ERROR_MESSAGE);
            return;
        }                
        else if(password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter password!!","Login Failed",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = StudentController.loginStudent(id.toLowerCase(), password);
        if (success) {
            JOptionPane.showMessageDialog(this,"Login successful!", "Success",JOptionPane.INFORMATION_MESSAGE);
            clearloginFields();
            CardLayout c12 = (CardLayout)(parentcard.getLayout());
            c12.show(parentcard, "card3");
            loadRecentInternships();
            resetNavColors();
            studentDashboardNav.setBackground(activeColor);

        } else {
            JOptionPane.showMessageDialog(this,
                    "User with this credentials not found!!", "Login Failed",JOptionPane.ERROR_MESSAGE);
        }  
        
    }//GEN-LAST:event_studentLoginActionPerformed

    private void jLabel81MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel81MouseMoved

    private void jLabel81FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel81FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel81FocusLost

    private void jLabel81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseClicked
        
        CardLayout c22 = (CardLayout)(cardPanel2.getLayout());
        c22.show(cardPanel2, "SignUP");
    }//GEN-LAST:event_jLabel81MouseClicked

    private void collegeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collegeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_collegeFieldActionPerformed

    private void signUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpButtonActionPerformed
        
        String id = collegeField.getText().trim().toLowerCase();
        String name = nameField.getText().trim();
        char[] passwordChars = passField.getPassword();
        String password = new String(passwordChars);
        String login = StudentController.registerNewStudent(id, name, password);

        if (login== null) {
            JOptionPane.showMessageDialog(this, "User registration  successful!", "Success",JOptionPane.INFORMATION_MESSAGE);
            clearloginFields();
            CardLayout c22 = (CardLayout)(cardPanel2.getLayout());
            c22.show(cardPanel2, "student");
            
            adminButton.setSelected(false);adminButton.setFocusPainted(false);
            studentButton.setSelected(true);studentButton.setFocusPainted(true);
            
        } else {
            JOptionPane.showMessageDialog(this, login, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_signUpButtonActionPerformed

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void passFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passFieldActionPerformed

    private void companyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyButtonActionPerformed
        InternshipController.sortByCompany() ;
        loadInternshipsToTable();
    }//GEN-LAST:event_companyButtonActionPerformed

    private void titleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleButtonActionPerformed
        InternshipController.sortByTitle() ;
        loadInternshipsToTable();
    }//GEN-LAST:event_titleButtonActionPerformed

    private void sCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sCompanyButtonActionPerformed
        InternshipController.sortByCompany();
        loadStudentInternshipCards();

    }//GEN-LAST:event_sCompanyButtonActionPerformed

    private void sTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sTypeButtonActionPerformed
        InternshipController.sortByType();
        loadStudentInternshipCards();

    }//GEN-LAST:event_sTypeButtonActionPerformed

    private void deadlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deadlineButtonActionPerformed
        InternshipController.sortByDeadline();
        loadInternshipsToTable();
    }//GEN-LAST:event_deadlineButtonActionPerformed

    private void sDeadlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sDeadlineButtonActionPerformed
        InternshipController.sortByDeadline();
        loadStudentInternshipCards();
    }//GEN-LAST:event_sDeadlineButtonActionPerformed

    private void companyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companyButtonMouseClicked
        resetSortButtonColors();
        companyButton.setBackground(activeColor);
    }//GEN-LAST:event_companyButtonMouseClicked

    private void durationButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_durationButtonMouseClicked
        resetSortButtonColors();
        durationButton.setBackground(activeColor);
    }//GEN-LAST:event_durationButtonMouseClicked

    private void deadlineButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deadlineButtonMouseClicked
        resetSortButtonColors();
        deadlineButton.setBackground(activeColor);
    }//GEN-LAST:event_deadlineButtonMouseClicked

    private void sTitleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sTitleButtonMouseClicked
        resetSortButtonColors();
        sTitleButton.setBackground(activeColor);
    }//GEN-LAST:event_sTitleButtonMouseClicked

    private void sCompanyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sCompanyButtonMouseClicked
        resetSortButtonColors();
        sCompanyButton.setBackground(activeColor);
    }//GEN-LAST:event_sCompanyButtonMouseClicked

    private void sTypeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sTypeButtonMouseClicked
        resetSortButtonColors();
        sTypeButton.setBackground(activeColor);
    }//GEN-LAST:event_sTypeButtonMouseClicked

    private void sDeadlineButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sDeadlineButtonMouseClicked
        resetSortButtonColors();
        sDeadlineButton.setBackground(activeColor);
    }//GEN-LAST:event_sDeadlineButtonMouseClicked

    private void appliedInternshipsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appliedInternshipsTableMouseClicked
                                                  
        int selectedRow = appliedInternshipsTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        String internshipId = appliedInternshipsTable.getValueAt(selectedRow, 0).toString();

        DefaultTableModel model = (DefaultTableModel) studentApplicationTable.getModel();
        model.setRowCount(0);

        HashSet<String> applicants = ApplicationController.viewApplicants(internshipId);

        for (String studentId : applicants) {
            model.Student s = StudentController.studentMap.get(studentId);
            if (s != null) {
                model.addRow(new Object[] {
                    s.getStudentId(),s.getStudentName(),s.getEmail()
                });
            }
        }
    }//GEN-LAST:event_appliedInternshipsTableMouseClicked
private void updateTable(LinkedList<Internship> results) {
    activeTableModel.setRowCount(0);
    for (Internship i : results) {
        Object[] row = {
            i.getId(),
            i.getTitle(),
            i.getCompany(),
            i.getType(),
            i.getDuration(),
            i.getDeadline()
        };
        activeTableModel.addRow(row);
    }
}
    private void btnSearchAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAdminActionPerformed
        String search = searchField.getText().trim();

        if (search.isEmpty() || search.equals("Search...")) {
            resetToPlaceholder();
            loadInternshipsToTable(); 
            return;
        }
        LinkedList<Internship> results = InternshipController.findSearch(search);

        if (!results.isEmpty()) {
            updateTable(results);
            resetNavColors();
            manageNav.setBackground(activeColor);
            CardLayout cl = (CardLayout)(cardPanel.getLayout());
            cl.show(cardPanel, "card3"); 
        } else {
            JOptionPane.showMessageDialog(this, "No results found for " + search);
        }

    }//GEN-LAST:event_btnSearchAdminActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
      String searchTxt = sSearchField.getText().trim();

    if (searchTxt.isEmpty() || searchTxt.equals("Search...")) {
        resetToPlaceholder();
        loadStudentInternshipCards();
        return;
    }
    LinkedList<Internship> results = InternshipController.findSearch(searchTxt);

    if (!results.isEmpty()) {
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card4");   
        resetNavColors();
        viewInternship.setBackground(activeColor); 
        displayStudentSearchResults(results); 
    } else {
        JOptionPane.showMessageDialog(this, "No internships found matching: " + searchTxt);
    }
    }//GEN-LAST:event_btnSearch1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        InternshipController.addDefaultInternship();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Admin;
    private javax.swing.JPanel AdminNav;
    private javax.swing.JPanel AdminPanel;
    private javax.swing.JPanel Login;
    private javax.swing.JPanel SignUP;
    private javax.swing.JPanel Student;
    private javax.swing.JPanel StudentPanel;
    private javax.swing.JPanel addNewInternship;
    private javax.swing.JPanel addNewInternship1;
    private javax.swing.JButton add_updateButton;
    private javax.swing.JButton adminButton;
    private javax.swing.JPanel adminNavigation;
    private javax.swing.JPanel adminNavigation3;
    private javax.swing.JPanel applicationNav;
    private javax.swing.JLabel applicationNo;
    private javax.swing.JPanel applicationsView;
    private javax.swing.JPanel applicationsView1;
    private javax.swing.JTable appliedInternshipsTable;
    private javax.swing.JButton applyButton;
    private javax.swing.JPanel archiveNav;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSearchAdmin;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel cardContainerPanel;
    private javax.swing.JPanel cardContainerPanel1;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel cardPanel1;
    private javax.swing.JPanel cardPanel2;
    private javax.swing.JTextField collegeField;
    private javax.swing.JTextField collegeIDField;
    private javax.swing.JButton companyButton;
    private javax.swing.JTextField companyField;
    private javax.swing.JTextField companyFieldS;
    private javax.swing.JPanel dashboardNav;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel dashboardPanel1;
    private javax.swing.JComboBox<String> dayCombo;
    private javax.swing.JComboBox<String> dayCombo1;
    private javax.swing.JButton deadlineButton;
    private javax.swing.JTable deletedInternshipTable;
    private javax.swing.JPanel deletedPanel;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JTextArea descriptionAreaS;
    private javax.swing.JButton durationButton;
    private javax.swing.JComboBox<String> durationCombo;
    private javax.swing.JComboBox<String> durationComboS;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JPanel gridPanel1;
    private javax.swing.JLabel indiviudalNoOfApplications;
    private javax.swing.JPanel internshipPreviewPanel;
    private javax.swing.JPanel internshipPreviewPanel1;
    private javax.swing.JScrollPane internshipScrollPane;
    private javax.swing.JTable internshipTable;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    private javax.swing.JPanel jPanel102;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel104;
    private javax.swing.JPanel jPanel105;
    private javax.swing.JPanel jPanel106;
    private javax.swing.JPanel jPanel107;
    private javax.swing.JPanel jPanel108;
    private javax.swing.JPanel jPanel109;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    private javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel118;
    private javax.swing.JPanel jPanel119;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel left;
    private javax.swing.JPanel logo_panel;
    private javax.swing.JPanel logoutNav;
    private javax.swing.JPanel manageInternship;
    private javax.swing.JPanel manageNav;
    private javax.swing.JComboBox<String> monthCombo;
    private javax.swing.JComboBox<String> monthComboS;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel noOfApplications;
    private javax.swing.JLabel noOfInternship;
    private javax.swing.JLabel noOfInternship1;
    private javax.swing.JLabel noOfStudent;
    private javax.swing.JLabel noOfStudent1;
    private javax.swing.JPanel parentcard;
    private javax.swing.JPasswordField passField;
    private javax.swing.JPasswordField passWordField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton restoreButton;
    private javax.swing.JPanel right;
    private javax.swing.JButton sCompanyButton;
    private javax.swing.JButton sDeadlineButton;
    private javax.swing.JTextField sSearchField;
    private javax.swing.JButton sTitleButton;
    private javax.swing.JButton sTypeButton;
    private javax.swing.JTextField salaryField;
    private javax.swing.JTextField salaryFieldS;
    private javax.swing.JTextField searchField;
    private javax.swing.JTextField settingCollegeID;
    private javax.swing.JPanel settingPanel;
    private javax.swing.JTextField settingsEmail;
    private javax.swing.JTextField settingsName;
    private javax.swing.JPanel settingsNav;
    private javax.swing.JTextField settingsPass;
    private javax.swing.JPanel sideImagePanel;
    private javax.swing.JButton signUpButton;
    private javax.swing.JTextArea skillArea;
    private javax.swing.JTextArea skillAreaS;
    private javax.swing.JPanel south;
    private javax.swing.JTable studentApplicationTable;
    private javax.swing.JButton studentButton;
    private javax.swing.JPanel studentCardContainerPanel;
    private javax.swing.JPanel studentDashboardNav;
    private javax.swing.JButton studentLogin;
    private javax.swing.JPanel studentNav1;
    private javax.swing.JPanel studentSettings;
    private javax.swing.JPanel studentsettingPanel1;
    private javax.swing.JButton titleButton;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField titleFieldS;
    private javax.swing.JComboBox<String> typeCombo;
    private javax.swing.JComboBox<String> typeComboS;
    private javax.swing.JButton viewDetails;
    private javax.swing.JPanel viewInternship;
    private javax.swing.JComboBox<String> yearCombo;
    private javax.swing.JComboBox<String> yearComboS;
    // End of variables declaration//GEN-END:variables
}
