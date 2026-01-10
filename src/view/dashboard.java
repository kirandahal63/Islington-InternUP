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
import controller.InternshipController;
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

    

    /**
     * Creates new form dashboard
     */
    DefaultTableModel activeTableModel;
    DefaultTableModel archivedTableModel;
    
    public dashboard() {
        initComponents();
        activeTableModel = (DefaultTableModel) internshipTable.getModel();
        loadInternshipsToTable();
        archivedTableModel = (DefaultTableModel) deletedInternshipTable.getModel();
        loadDeletedInternshipsToTable();
        
        studentCardContainerPanel.setLayout(new GridBagLayout());
        studentCardContainerPanel.setBackground(new Color(245, 245, 245));
        
        internshipScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        internshipScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        loadInternshipCards() ;
        cardContainerPanel.setLayout(new GridLayout(0, 2, 15, 15));
        studentCardContainerPanel.setLayout(new GridLayout(0, 3, 15, 15));
        

        

    }
    private void loadInternshipsToTable() {

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
    
    private void resetNavColors() {
        dashboardNav.setBackground(defaultColor);
        manageNav.setBackground(defaultColor);
        archiveNav.setBackground(defaultColor);
        settingsNav.setBackground(defaultColor);
        applicationNav.setBackground(defaultColor);
        logoutNav.setBackground(defaultColor);
    }
    private void resetSortButtonColors() {
        companyButton.setBackground(defaultColor);
        deadlineButton.setBackground(defaultColor);
        typeButton.setBackground(defaultColor);
        salaryButton.setBackground(defaultColor);
    }

    private void loadInternshipCards() {

    cardContainerPanel.removeAll();
    int limit = 8;   
    int count = 0;

    for (int i = InternshipController.internshipList.size() - 1; i >= 0; i--) {

        if (count == limit) {
            break;
        }

        Internship internship = InternshipController.internshipList.get(i);
        JPanel card = createInternshipCard(internship);
        cardContainerPanel.add(card);
        count++;
    }

    cardContainerPanel.revalidate();
    cardContainerPanel.repaint();
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

    JLabel titleLabel = new JLabel(i.getTitle());
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel companyLabel = new JLabel(i.getCompany());
    companyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

    JLabel metaLabel = new JLabel(
            "Type: " + i.getType() + " | Deadline: " + i.getDeadline()
    );
    metaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    metaLabel.setForeground(Color.GRAY);

    JPanel textPanel = new JPanel(new GridLayout(0, 1));
    textPanel.setBackground(Color.WHITE);

    textPanel.add(titleLabel);
    textPanel.add(companyLabel);
    textPanel.add(metaLabel);

    card.add(textPanel, BorderLayout.CENTER);

    // click handling
    card.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            //openInternship(i);
        }
    });

    return card;
}
   
    
    
private void loadStudentInternshipCards() {
    studentCardContainerPanel.removeAll();

    // Set GridBagLayout for dynamic placement
    studentCardContainerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // space between cards
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int col = 0;
    int row = 0;

    for (Internship internship : InternshipController.internshipList) {
        JPanel card = createStudentInternshipCard(internship);

        gbc.gridx = col;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // stretch horizontally
        studentCardContainerPanel.add(card, gbc);

        col++;
        if (col == 3) { // 3 columns
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

private JPanel createStudentInternshipCard(Internship i) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);

    // Fixed height
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
        jLabel35 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        right = new javax.swing.JPanel();
        left = new javax.swing.JPanel();
        south = new javax.swing.JPanel();
        cardPanel2 = new javax.swing.JPanel();
        AdminPanel = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jButton12 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        StudentPanel = new javax.swing.JPanel();
        AdminPanel1 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButton13 = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        SignupPanel = new javax.swing.JPanel();
        StudentPanel1 = new javax.swing.JPanel();
        AdminPanel2 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel66 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel21 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        logo_panel = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        Admin = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        cardPanel = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        gridPanel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
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
        companyButton = new javax.swing.JButton();
        deadlineButton = new javax.swing.JButton();
        typeButton = new javax.swing.JButton();
        salaryButton = new javax.swing.JButton();
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
        jPanel64 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jPanel65 = new javax.swing.JPanel();
        studentNav1 = new javax.swing.JPanel();
        adminNavigation3 = new javax.swing.JPanel();
        studentDashboardNav = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel74 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
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
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel71 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel78 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jPanel80 = new javax.swing.JPanel();
        jPanel81 = new javax.swing.JPanel();
        jPanel82 = new javax.swing.JPanel();
        jPanel83 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel61 = new javax.swing.JLabel();
        applicationsView1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
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
        add_updateButton1 = new javax.swing.JButton();
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

        parentcard.setLayout(new java.awt.CardLayout());

        Login.setLayout(new java.awt.BorderLayout());

        sideImagePanel.setBackground(new java.awt.Color(102, 102, 255));

        ImageIcon icon5 = new ImageIcon("images/login.jpg");
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
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
        );

        Login.add(sideImagePanel, java.awt.BorderLayout.LINE_START);

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setLayout(new java.awt.BorderLayout());

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setPreferredSize(new java.awt.Dimension(455, 140));

        ImageIcon icon = new ImageIcon("images/internupLogo.png");
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
        jLabel3.setIcon(new ImageIcon(scaled));
        jLabel35.setPreferredSize(new java.awt.Dimension(50, 70));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(564, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
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
            .addGap(0, 324, Short.MAX_VALUE)
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
            .addGap(0, 324, Short.MAX_VALUE)
        );

        jPanel1.add(left, java.awt.BorderLayout.LINE_START);

        south.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout southLayout = new javax.swing.GroupLayout(south);
        south.setLayout(southLayout);
        southLayout.setHorizontalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
        southLayout.setVerticalGroup(
            southLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(south, java.awt.BorderLayout.PAGE_END);

        cardPanel2.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel2.setLayout(new java.awt.CardLayout());

        AdminPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setText("Username");

        jLabel39.setText("Password");

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(167, 139, 250));
        jButton12.setText("Login");
        jButton12.setBorder(null);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTextField2.setText("jTextField2");

        javax.swing.GroupLayout AdminPanelLayout = new javax.swing.GroupLayout(AdminPanel);
        AdminPanel.setLayout(AdminPanelLayout);
        AdminPanelLayout.setHorizontalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                .addGap(134, 134, 134))
        );
        AdminPanelLayout.setVerticalGroup(
            AdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardPanel2.add(AdminPanel, "AdminPanel");

        StudentPanel.setBackground(new java.awt.Color(255, 255, 255));

        AdminPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setText("College ID");

        jLabel62.setText("Password");

        jPasswordField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField2ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(167, 139, 250));
        jButton13.setText("Login");
        jButton13.setBorder(null);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel63.setText("Don't have an account? Sign Up");
        jLabel63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel63.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel63MouseMoved(evt);
            }
        });
        jLabel63.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLabel63FocusLost(evt);
            }
        });
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout AdminPanel1Layout = new javax.swing.GroupLayout(AdminPanel1);
        AdminPanel1.setLayout(AdminPanel1Layout);
        AdminPanel1Layout.setHorizontalGroup(
            AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanel1Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminPanel1Layout.createSequentialGroup()
                        .addGroup(AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addGroup(AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel62))))
                        .addGap(14, 14, 14))))
        );
        AdminPanel1Layout.setVerticalGroup(
            AdminPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addGap(12, 12, 12)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel63)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout StudentPanelLayout = new javax.swing.GroupLayout(StudentPanel);
        StudentPanel.setLayout(StudentPanelLayout);
        StudentPanelLayout.setHorizontalGroup(
            StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentPanelLayout.createSequentialGroup()
                .addComponent(AdminPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        StudentPanelLayout.setVerticalGroup(
            StudentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentPanelLayout.createSequentialGroup()
                .addComponent(AdminPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cardPanel2.add(StudentPanel, "StudentPanel");

        SignupPanel.setBackground(new java.awt.Color(255, 255, 255));

        StudentPanel1.setBackground(new java.awt.Color(255, 255, 255));

        AdminPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel64.setText("College ID");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel65.setText("Email");

        jButton14.setBackground(new java.awt.Color(167, 139, 250));
        jButton14.setText("Sign Up");
        jButton14.setBorder(null);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel66.setText("Set Password");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jPasswordField3.setPreferredSize(new java.awt.Dimension(90, 34));

        javax.swing.GroupLayout AdminPanel2Layout = new javax.swing.GroupLayout(AdminPanel2);
        AdminPanel2.setLayout(AdminPanel2Layout);
        AdminPanel2Layout.setHorizontalGroup(
            AdminPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(AdminPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jTextField9)
                    .addComponent(jLabel66)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jLabel64)
                    .addComponent(jLabel65)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        AdminPanel2Layout.setVerticalGroup(
            AdminPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout StudentPanel1Layout = new javax.swing.GroupLayout(StudentPanel1);
        StudentPanel1.setLayout(StudentPanel1Layout);
        StudentPanel1Layout.setHorizontalGroup(
            StudentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 409, Short.MAX_VALUE)
            .addGroup(StudentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AdminPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        StudentPanel1Layout.setVerticalGroup(
            StudentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(StudentPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AdminPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout SignupPanelLayout = new javax.swing.GroupLayout(SignupPanel);
        SignupPanel.setLayout(SignupPanelLayout);
        SignupPanelLayout.setHorizontalGroup(
            SignupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
            .addGroup(SignupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SignupPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(StudentPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        SignupPanelLayout.setVerticalGroup(
            SignupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
            .addGroup(SignupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SignupPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(StudentPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardPanel2.add(SignupPanel, "SignupPanel");

        jPanel1.add(cardPanel2, java.awt.BorderLayout.CENTER);

        jPanel21.setPreferredSize(new java.awt.Dimension(614, 230));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));

        jButton16.setText("Admin");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton15.setText("Student");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(jButton16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton15))
                .addGap(44, 44, 44))
        );

        jPanel21.add(jPanel60, java.awt.BorderLayout.CENTER);

        logo_panel.setBackground(new java.awt.Color(255, 255, 255));

        ImageIcon icon13 = new ImageIcon("images/internupLogo.png");
        Image imgg3 = icon13.getImage();
        Image scaledup10 = imgg3.getScaledInstance(160, 190, Image.SCALE_SMOOTH);
        jLabel36.setIcon(new ImageIcon(scaledup10));

        ImageIcon icon68 = new ImageIcon("images/collegeLogo.png");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
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

        ImageIcon icon9 = new ImageIcon("images/internupLogo.png");
        Image imgg = icon9.getImage();
        Image scaled10 = imgg.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaled10));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Hello");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel17.setText("Admin");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("Search");
        jTextField1.setPreferredSize(new java.awt.Dimension(64, 25));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 608, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(61, 61, 61))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))))
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
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("5");
        jPanel13.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel7.setText("jLabel4");
        jPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 148));

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

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel6.setText("jLabel4");
        jPanel11.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 150));

        gridPanel.add(jPanel11);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Active Internship ");
        jPanel14.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("75");
        jPanel14.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 50, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jPanel14.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 150));

        gridPanel.add(jPanel14);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("No of Application Received");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("113");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel5.setText("jLabel4");
        jPanel9.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 151));

        gridPanel.add(jPanel9);

        jPanel17.add(gridPanel, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel17, java.awt.BorderLayout.CENTER);

        dashboardPanel.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(153, 153, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(300, 392));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setPreferredSize(new java.awt.Dimension(300, 494));
        jPanel44.setVerifyInputWhenFocusTarget(false);

        ImageIcon icon91 = new ImageIcon("images/companies.png");
        Image imgg1 = icon91.getImage();
        Image scaledCom = imgg1.getScaledInstance(380, 232, Image.SCALE_SMOOTH);
        jLabel67.setIcon(new ImageIcon(scaledCom));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel44, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel19, java.awt.BorderLayout.LINE_END);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(704, 464));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setText("Recent Intenships");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel33)
                .addContainerGap(571, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(22, 22, 22))
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
            .addGap(0, 364, Short.MAX_VALUE)
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
            .addGap(0, 704, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
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

        companyButton.setText("Sort By Company");
        companyButton.setPreferredSize(new java.awt.Dimension(150, 30));
        companyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                companyButtonMouseClicked(evt);
            }
        });

        deadlineButton.setText("Sort By Deadline");
        deadlineButton.setPreferredSize(new java.awt.Dimension(150, 30));

        typeButton.setText("Sort by Type");
        typeButton.setPreferredSize(new java.awt.Dimension(150, 30));
        typeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeButtonActionPerformed(evt);
            }
        });

        salaryButton.setText("Sort by Salary");
        salaryButton.setPreferredSize(new java.awt.Dimension(150, 30));

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
                        .addComponent(companyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(salaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
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
                        .addComponent(deadlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(companyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(typeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(salaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGap(0, 490, Short.MAX_VALUE)
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
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel29, java.awt.BorderLayout.LINE_END);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setPreferredSize(new java.awt.Dimension(904, 20));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
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
                .addContainerGap(321, Short.MAX_VALUE)
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
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 674, Short.MAX_VALUE)
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
            .addGap(0, 674, Short.MAX_VALUE)
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

        dayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
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
                .addContainerGap(524, Short.MAX_VALUE)
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
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 554, Short.MAX_VALUE)
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
                .addContainerGap(804, Short.MAX_VALUE)
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
            .addGap(0, 554, Short.MAX_VALUE)
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

        javax.swing.GroupLayout applicationsViewLayout = new javax.swing.GroupLayout(applicationsView);
        applicationsView.setLayout(applicationsViewLayout);
        applicationsViewLayout.setHorizontalGroup(
            applicationsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
        );
        applicationsViewLayout.setVerticalGroup(
            applicationsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );

        cardPanel.add(applicationsView, "card4");

        settingPanel.setLayout(new java.awt.BorderLayout());

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 1004, Short.MAX_VALUE)
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
            .addGap(0, 514, Short.MAX_VALUE)
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
            .addGap(0, 514, Short.MAX_VALUE)
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

        adminNavigation.setPreferredSize(new java.awt.Dimension(13, 185));
        adminNavigation.setLayout(new java.awt.GridLayout(6, 1, 0, 1));

        dashboardNav.setBackground(new java.awt.Color(153, 153, 255));
        dashboardNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation.add(manageNav);

        archiveNav.setBackground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(15, 15, 15))
        );
        archiveNavLayout.setVerticalGroup(
            archiveNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, archiveNavLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation.add(archiveNav);

        applicationNav.setBackground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(64, Short.MAX_VALUE))
        );
        applicationNavLayout.setVerticalGroup(
            applicationNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(applicationNavLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel19)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        adminNavigation.add(applicationNav);

        settingsNav.setBackground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(79, Short.MAX_VALUE))
        );
        settingsNavLayout.setVerticalGroup(
            settingsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsNavLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addContainerGap(9, Short.MAX_VALUE))
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
            .addGap(0, 714, Short.MAX_VALUE)
            .addGroup(AdminNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AdminNavLayout.createSequentialGroup()
                    .addComponent(adminNavigation, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 451, Short.MAX_VALUE)))
        );

        jPanel2.add(AdminNav, "card4");

        Admin.add(jPanel2, java.awt.BorderLayout.LINE_START);

        parentcard.add(Admin, "card5");

        Student.setLayout(new java.awt.BorderLayout());

        jPanel61.setBackground(new java.awt.Color(255, 255, 255));
        jPanel61.setPreferredSize(new java.awt.Dimension(933, 80));

        ImageIcon icon12 = new ImageIcon("images/internupLogo.png");
        Image imgaa = icon12.getImage();
        Image scaled12 = imgaa.getScaledInstance(150, 180, Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(scaled12));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setText("Hello");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel42.setText("Student");

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField4.setText("Search");
        jTextField4.setPreferredSize(new java.awt.Dimension(64, 25));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 537, Short.MAX_VALUE)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(42, 42, 42))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        Student.add(jPanel61, java.awt.BorderLayout.PAGE_START);

        jPanel65.setBackground(new java.awt.Color(255, 255, 255));
        jPanel65.setPreferredSize(new java.awt.Dimension(170, 544));
        jPanel65.setLayout(new java.awt.CardLayout());

        studentNav1.setBackground(new java.awt.Color(255, 255, 255));
        studentNav1.setAutoscrolls(true);

        adminNavigation3.setPreferredSize(new java.awt.Dimension(13, 185));
        adminNavigation3.setLayout(new java.awt.GridLayout(5, 1, 0, 1));

        studentDashboardNav.setBackground(new java.awt.Color(153, 153, 255));
        studentDashboardNav.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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

        jPanel73.setBackground(new java.awt.Color(255, 255, 255));
        jPanel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel73MouseClicked(evt);
            }
        });

        jLabel51.setText("View Internships");

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addGap(35, 35, 35))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation3.add(jPanel73);

        jPanel74.setBackground(new java.awt.Color(255, 255, 255));
        jPanel74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel74MouseClicked(evt);
            }
        });

        jLabel52.setText("Applied Internships");

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel74Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel52)
                .addGap(15, 15, 15))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        adminNavigation3.add(jPanel74);

        jPanel75.setBackground(new java.awt.Color(255, 255, 255));
        jPanel75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel75MouseClicked(evt);
            }
        });

        jLabel53.setText("Settings");

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel53)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel53)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        adminNavigation3.add(jPanel75);

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
                .addGap(0, 484, Short.MAX_VALUE))
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

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Total Students");
        jPanel70.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("5");
        jPanel70.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel45.setText("jLabel4");
        jPanel70.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 148));

        gridPanel1.add(jPanel70);

        jPanel71.setBackground(new java.awt.Color(255, 255, 255));
        jPanel71.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel71.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Partner Companies");
        jPanel71.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("200+");
        jPanel71.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel48.setText("jLabel4");
        jPanel71.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 150));

        gridPanel1.add(jPanel71);

        jPanel77.setBackground(new java.awt.Color(255, 255, 255));
        jPanel77.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel77.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Active Internship ");
        jPanel77.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("75");
        jPanel77.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 50, 40));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jPanel77.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 150));

        gridPanel1.add(jPanel77);

        jPanel78.setBackground(new java.awt.Color(255, 255, 255));
        jPanel78.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel78.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("No of Application Received");
        jPanel78.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("113");
        jPanel78.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/background.png"))); // NOI18N
        jLabel60.setText("jLabel4");
        jPanel78.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 151));

        gridPanel1.add(jPanel78);

        jPanel68.add(gridPanel1, java.awt.BorderLayout.CENTER);

        jPanel66.add(jPanel68, java.awt.BorderLayout.CENTER);

        dashboardPanel1.add(jPanel66, java.awt.BorderLayout.PAGE_START);

        jPanel79.setLayout(new java.awt.BorderLayout());

        jPanel80.setBackground(new java.awt.Color(153, 153, 255));
        jPanel80.setPreferredSize(new java.awt.Dimension(210, 392));
        jPanel80.setLayout(new java.awt.BorderLayout());

        jPanel81.setBackground(new java.awt.Color(255, 255, 255));
        jPanel81.setPreferredSize(new java.awt.Dimension(20, 464));

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        jPanel80.add(jPanel81, java.awt.BorderLayout.LINE_END);

        jPanel82.setPreferredSize(new java.awt.Dimension(210, 464));

        jPanel83.setBackground(new java.awt.Color(204, 204, 255));
        jPanel83.setPreferredSize(new java.awt.Dimension(194, 337));

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 178, Short.MAX_VALUE)
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel80.add(jPanel82, java.awt.BorderLayout.CENTER);

        jPanel79.add(jPanel80, java.awt.BorderLayout.LINE_END);

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));
        jPanel84.setPreferredSize(new java.awt.Dimension(724, 464));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable4);

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setText("Recent Posts");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        jPanel79.add(jPanel84, java.awt.BorderLayout.CENTER);

        dashboardPanel1.add(jPanel79, java.awt.BorderLayout.CENTER);

        cardPanel1.add(dashboardPanel1, "dashboard");

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
            .addGap(0, 518, Short.MAX_VALUE)
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
            .addGap(0, 518, Short.MAX_VALUE)
        );

        applicationsView1.add(jPanel62, java.awt.BorderLayout.LINE_END);

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel69.setText("All Internships");

        jButton18.setText("Sort By Company");
        jButton18.setPreferredSize(new java.awt.Dimension(150, 30));

        jButton19.setText("Sort By Deadline");
        jButton19.setPreferredSize(new java.awt.Dimension(150, 30));

        jButton1.setText("Sort by Type");

        jButton20.setText("Sort By Title");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
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
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel70.setText("System Information");
        jLabel70.setPreferredSize(new java.awt.Dimension(55, 50));

        javax.swing.GroupLayout jPanel112Layout = new javax.swing.GroupLayout(jPanel112);
        jPanel112.setLayout(jPanel112Layout);
        jPanel112Layout.setHorizontalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel112Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel115Layout = new javax.swing.GroupLayout(jPanel115);
        jPanel115.setLayout(jPanel115Layout);
        jPanel115Layout.setHorizontalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel115Layout.setVerticalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
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
            .addGap(0, 514, Short.MAX_VALUE)
        );

        jPanel114.add(jPanel116, java.awt.BorderLayout.LINE_END);

        jPanel117.setBackground(new java.awt.Color(255, 255, 255));
        jPanel117.setLayout(new java.awt.BorderLayout());
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
            .addGap(0, 674, Short.MAX_VALUE)
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
            .addGap(0, 674, Short.MAX_VALUE)
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

        add_updateButton1.setBackground(new java.awt.Color(153, 153, 255));
        add_updateButton1.setText("Apply");
        add_updateButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_updateButton1ActionPerformed(evt);
            }
        });
        jPanel95.add(add_updateButton1);

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
        CardLayout c2 = (CardLayout)(cardPanel.getLayout());
        c2.show(cardPanel, "dashboard");         
        resetNavColors();
        dashboardNav.setBackground(activeColor);
    }//GEN-LAST:event_dashboardNavMouseClicked

    private void manageNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageNavMouseClicked
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "card3"); 
        resetNavColors();
        manageNav.setBackground(activeColor);
        
    }//GEN-LAST:event_manageNavMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        CardLayout c3 = (CardLayout)(cardPanel.getLayout());
        c3.show(cardPanel, "card5");
        isUpdateMode = false;
        editingIndex = -1;
        add_updateButton.setText("Post Internship");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void typeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeButtonActionPerformed

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

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void studentDashboardNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentDashboardNavMouseClicked
        CardLayout c2 = (CardLayout)(cardPanel.getLayout());
        c2.show(cardPanel, "dashboard");         
        resetNavColors();
        dashboardNav.setBackground(activeColor);
    }//GEN-LAST:event_studentDashboardNavMouseClicked

    private void jPanel73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel73MouseClicked
        CardLayout cl = (CardLayout)(cardPanel1.getLayout());
        cl.show(cardPanel1, "card4"); 
        resetNavColors();
        manageNav.setBackground(activeColor);
        loadStudentInternshipCards();
    }//GEN-LAST:event_jPanel73MouseClicked

    private void jPanel74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel74MouseClicked
       CardLayout c2 = (CardLayout)(cardPanel.getLayout());
        c2.show(cardPanel, "card4");         
        resetNavColors();
        dashboardNav.setBackground(activeColor);
    }//GEN-LAST:event_jPanel74MouseClicked

    private void jPanel75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel75MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel75MouseClicked

    private void jPanel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel76MouseClicked
        CardLayout c14 = (CardLayout)(parentcard.getLayout());
        c14.show(parentcard, "card4"); 
        resetNavColors();
    }//GEN-LAST:event_jPanel76MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        CardLayout c2 = (CardLayout)(cardPanel2.getLayout());
        c2.show(cardPanel2, "StudentPanel");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed

        CardLayout c2 = (CardLayout)(cardPanel2.getLayout());
        c2.show(cardPanel2, "AdminPanel");

    }//GEN-LAST:event_jButton16ActionPerformed
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
        }else{
            
            deadline = year + "-" + month + "-" + day;
            
            if (isUpdateMode) 
            {
                if (InternshipController.isSameAsOriginal( editingIndex, title, company, deadline,salary, type, duration, description, requirement)) 
                {
                    JOptionPane.showMessageDialog(this,"You must change at least one field to update.","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Full duplicate exists elsewhere
                if (InternshipController.isDuplicateForUpdate(editingIndex, title, company, deadline,salary, type, duration, description, requirement)) 
                {
                    JOptionPane.showMessageDialog(this,"An internship with the same details already exists.","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int userChoice2 = JOptionPane.showConfirmDialog(this,"Are you sure you want to update Internship.", 
                                                                    "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userChoice2 == JOptionPane.YES_OPTION) {
                    InternshipController.updateInternship(editingIndex, title, company,deadline, salary, type, duration, description, requirement);
                    loadInternshipsToTable();
                    clearInternshipForm();
                    JOptionPane.showMessageDialog(this, "Internship updated successfully.","Success", JOptionPane.INFORMATION_MESSAGE);

                    isUpdateMode = false;
                    editingIndex = -1;
                    add_updateButton.setText("Post Internship");
                    CardLayout cl = (CardLayout)(cardPanel.getLayout());
                    cl.show(cardPanel, "card3"); 
                     
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
            JOptionPane.showMessageDialog(this, "Please select an internship to delete.",
                                                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete this internship?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = InternshipController.deleteInternship(selectedRow);
            if (success) {
                JOptionPane.showMessageDialog(this, "Internship deleted successfully.",
                                              "Success", JOptionPane.INFORMATION_MESSAGE);
                loadInternshipsToTable();
                loadDeletedInternshipsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete internship.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
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

    private void add_updateButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_updateButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_updateButton1ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        
    }//GEN-LAST:event_jButton20ActionPerformed

    private void companyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companyButtonMouseClicked
        resetSortButtonColors();
        companyButton.setBackground(activeColor);
    }//GEN-LAST:event_companyButtonMouseClicked

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        CardLayout c4 = (CardLayout)(cardPanel.getLayout());
        c4.show(cardPanel, "StudentPanel");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        CardLayout c3 = (CardLayout)(cardPanel2.getLayout());
        c3.show(cardPanel2, "SignupPanel");
    }//GEN-LAST:event_jLabel63MouseClicked

    private void jLabel63FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel63FocusLost

    }//GEN-LAST:event_jLabel63FocusLost

    private void jLabel63MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseMoved

    }//GEN-LAST:event_jLabel63MouseMoved

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        CardLayout c12 = (CardLayout)(parentcard.getLayout());
        c12.show(parentcard, "card3");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jPasswordField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        CardLayout c13 = (CardLayout)(parentcard.getLayout());
        c13.show(parentcard, "card5");

        /*
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()); // for JPasswordField

        AdminController adminController = new AdminController();
        String result = adminController.login(username, password);

        if (result == null) {
            JOptionPane.showMessageDialog(this, "Admin Login successful!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
            CardLayout c13 = (CardLayout)(parentcard.getLayout());
            c13.show(parentcard, "card5");
        } else {
            JOptionPane.showMessageDialog(this, result, "Login Error", JOptionPane.ERROR_MESSAGE);
        }*/
    }//GEN-LAST:event_jButton12ActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

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
    private javax.swing.JPanel AdminPanel1;
    private javax.swing.JPanel AdminPanel2;
    private javax.swing.JPanel Login;
    private javax.swing.JPanel SignupPanel;
    private javax.swing.JPanel Student;
    private javax.swing.JPanel StudentPanel;
    private javax.swing.JPanel StudentPanel1;
    private javax.swing.JPanel addNewInternship;
    private javax.swing.JPanel addNewInternship1;
    private javax.swing.JButton add_updateButton;
    private javax.swing.JButton add_updateButton1;
    private javax.swing.JPanel adminNavigation;
    private javax.swing.JPanel adminNavigation3;
    private javax.swing.JPanel applicationNav;
    private javax.swing.JPanel applicationsView;
    private javax.swing.JPanel applicationsView1;
    private javax.swing.JPanel archiveNav;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel cardContainerPanel;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel cardPanel1;
    private javax.swing.JPanel cardPanel2;
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
    private javax.swing.JComboBox<String> durationCombo;
    private javax.swing.JComboBox<String> durationComboS;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JPanel gridPanel1;
    private javax.swing.JPanel internshipPreviewPanel;
    private javax.swing.JScrollPane internshipScrollPane;
    private javax.swing.JTable internshipTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    private javax.swing.JPanel jPanel117;
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
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
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
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel left;
    private javax.swing.JPanel logo_panel;
    private javax.swing.JPanel logoutNav;
    private javax.swing.JPanel manageInternship;
    private javax.swing.JPanel manageNav;
    private javax.swing.JComboBox<String> monthCombo;
    private javax.swing.JComboBox<String> monthComboS;
    private javax.swing.JPanel parentcard;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton restoreButton;
    private javax.swing.JPanel right;
    private javax.swing.JButton salaryButton;
    private javax.swing.JTextField salaryField;
    private javax.swing.JTextField salaryFieldS;
    private javax.swing.JPanel settingPanel;
    private javax.swing.JPanel settingsNav;
    private javax.swing.JPanel sideImagePanel;
    private javax.swing.JTextArea skillArea;
    private javax.swing.JTextArea skillAreaS;
    private javax.swing.JPanel south;
    private javax.swing.JPanel studentCardContainerPanel;
    private javax.swing.JPanel studentDashboardNav;
    private javax.swing.JPanel studentNav1;
    private javax.swing.JPanel studentsettingPanel1;
    private javax.swing.JTextField titleField;
    private javax.swing.JTextField titleFieldS;
    private javax.swing.JButton typeButton;
    private javax.swing.JComboBox<String> typeCombo;
    private javax.swing.JComboBox<String> typeComboS;
    private javax.swing.JButton viewDetails;
    private javax.swing.JComboBox<String> yearCombo;
    private javax.swing.JComboBox<String> yearComboS;
    // End of variables declaration//GEN-END:variables
}
