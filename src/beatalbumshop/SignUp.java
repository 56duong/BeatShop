package beatalbumshop;

import beatalbumshop.componment.MyDialog;
import beatalbumshop.dao.CustomerDAO;
import beatalbumshop.dao.CustomerDAOImpl;
import beatalbumshop.dao.Firebase;
import beatalbumshop.dao.UserDAO;
import beatalbumshop.dao.UserDAOImpl;
import beatalbumshop.model.Customer;
import beatalbumshop.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class SignUp extends javax.swing.JFrame {

    ArrayList<User> listUser = new ArrayList<>();
    UserDAO userDAO = new UserDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();

    public SignUp() {
        initComponents();
        setLocationRelativeTo(null);
        
        //rounded frame
        setShape(new RoundRectangle2D.Double(0, 0, 1280, 720, 20, 20));
        setSize(1280, 720);
        setLocationRelativeTo(null);
        
        addPlaceholderText(txtEmail, "Email");
        addPlaceholderText(txtPassword, "Password");
        addPlaceholderText(txtPassword2, "Password");
        
        txtEmail.requestFocus();
    }
    
    
    
    private void addPlaceholderText(JTextField textField, String placeholderText) {
        // Save the default foreground color of the text field
        Color defaultColor = textField.getForeground();
        
        // Set the placeholder text
        textField.setText(placeholderText);

        // Add a focus listener to handle the placeholder text
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(defaultColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholderText);
                }
            }
        });
    }
    
    
    
    public static int getMaxID(CollectionReference colRef, String column) {
        try {
            // Create a query to order the documents by albumID in descending order
            Query query = colRef.orderBy(column, Query.Direction.DESCENDING).limit(1);

            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();

            try {
                QuerySnapshot querySnapshot = querySnapshotFuture.get();
                if (!querySnapshot.isEmpty()) {
                    // Retrieve the first document (with the maximum albumID)
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    // Get the value of the albumID field
                    return document.getLong(column).intValue();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    
    
    public static void createUser(String email, String password) {
//        Firestore db = Firebase.getFirestore("beat-75a88");
//        CollectionReference colRef = db.collection("users");
//        int userID = getMaxID(colRef, "userID") + 1;
//
//        Date currentDate = new Date();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String formattedDate = dateFormat.format(currentDate);
//        //INSERT 
//        User user = new User(userID, email, password, formattedDate, 0);
//        ApiFuture<WriteResult> result = colRef.document(userID + "").set(user);
//
//        try {
//            System.out.println("Update time : " + result.get().getUpdateTime());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    
    
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
        return email.matches(emailRegex);
    }

    
    
    public boolean checkUser() {
//        listUser = (ArrayList<User>) userDAO.getAll();
//        if (listUser != null) {
//            for (User user : listUser) {
//                if (user.getEmail().equalsIgnoreCase(txtEmail.getText())) {
//                    return false;
//                }
//            }
//        }
        return true;
    }

    
    
    public String validateFormSigup(String email, String password, String confirmPassword) {
        // Kiểm tra tính hợp lệ của email
        if (email.isEmpty()) {
            txtEmail.requestFocus();
            return "Vui lòng nhập Email";
        } else if (!isValidEmail(email)) {
            txtEmail.requestFocus();
            return "Email sai định dạng";

        }

        if (password.isEmpty()) {
            txtPassword.requestFocus();
            return "Vui lòng nhập Pasword";
        }
        if (!password.equals(confirmPassword)) {
            txtPassword2.requestFocus();
            return "Password và Password Confirm không khớp";
        }
        return null; // Trả về null nếu form hợp lệ
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        windowTitleBar1 = new beatalbumshop.componment.WindowTitleBar();
        pnlContent = new javax.swing.JPanel();
        pnlForm = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtPassword2 = new javax.swing.JPasswordField();
        btnSignup = new beatalbumshop.componment.MyButton();
        lblLogin = new javax.swing.JLabel();
        lblContinueAs = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1280, 720));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1280, 720));

        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlMain.setLayout(new java.awt.BorderLayout());

        windowTitleBar1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlMain.add(windowTitleBar1, java.awt.BorderLayout.PAGE_START);

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));

        pnlForm.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Sign Up");

        txtEmail.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(82, 82, 82));
        txtEmail.setText("Email");
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        txtEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtPassword.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(82, 82, 82));
        txtPassword.setText("jPasswordField1");
        txtPassword.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));

        txtPassword2.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtPassword2.setForeground(new java.awt.Color(82, 82, 82));
        txtPassword2.setText("jPasswordField1");
        txtPassword2.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));

        btnSignup.setBackground(new java.awt.Color(0, 0, 0));
        btnSignup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sign up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        lblLogin.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setText("ALREADY HAVE AN ACCOUNT? LOG IN");
        lblLogin.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        lblLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });

        lblContinueAs.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblContinueAs.setText("CONTINUE AS A GUEST");
        lblContinueAs.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        lblContinueAs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblContinueAs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblContinueAsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblTitle)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLogin)
                            .addComponent(lblContinueAs))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSignup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50)
                .addComponent(txtEmail)
                .addGap(30, 30, 30)
                .addComponent(txtPassword)
                .addGap(30, 30, 30)
                .addComponent(txtPassword2)
                .addGap(30, 30, 30)
                .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(50, 50, 50)
                .addComponent(lblLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(lblContinueAs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(433, 433, 433)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );

        pnlMain.add(pnlContent, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblContinueAsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContinueAsMouseClicked
        Window[] windows = Window.getWindows();
        if(windows.length <= 1) {
            dispose();
            new Main().setVisible(true);
        }
    }//GEN-LAST:event_lblContinueAsMouseClicked

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        dispose();
        new LogIn().setVisible(true);
    }//GEN-LAST:event_lblLoginMouseClicked

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmpassword = new String(txtPassword2.getPassword());
        String errorMessage = validateFormSigup(email, password, confirmpassword);
        
        // co loi
        if (errorMessage != null) {
            MyDialog.display(1, errorMessage);
        }
        else {
            // kiem tra ton tai
            if (customerDAO.checkExitByEmail(email) && userDAO.checkExitByEmail(email)) {
                Firestore db = Firebase.getFirestore("beat-75a88");
                CollectionReference colRef = db.collection("customers");
                boolean result = customerDAO.add(new Customer(null, getMaxID(colRef, "customerID"), email, password));
                if(result) {
                    dispose();
                    new LogIn().setVisible(true);
                    MyDialog.display(0, "Đăng ký thành công!");
                }
            }
            else {
                txtEmail.requestFocus();
                MyDialog.display(1, "Email đã tồn tại vui lòng thử lại");
            }
        }
    }//GEN-LAST:event_btnSignupActionPerformed

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
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnSignup;
    private javax.swing.JLabel lblContinueAs;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword2;
    private beatalbumshop.componment.WindowTitleBar windowTitleBar1;
    // End of variables declaration//GEN-END:variables
}
