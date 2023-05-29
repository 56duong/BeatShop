/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package beatalbumshop;

import beatalbumshop.componment.MyDialog;
import beatalbumshop.dao.Firebase;
import beatalbumshop.dao.UserDAO;
import beatalbumshop.dao.UserDAOImpl;
import beatalbumshop.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class SignUp extends javax.swing.JFrame {

    ArrayList<User> listUser = new ArrayList<>();
    UserDAO userDAO = new UserDAOImpl();

    public SignUp() {
        initComponents();
        setLocationRelativeTo(null);
        
        addPlaceholderText(txtEmail2, "Email");
        addPlaceholderText(txtPassword, "Password");
        addPlaceholderText(txtPassword2, "Password");
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
                    return document.getLong("userID").intValue();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static void createUser(String email, String password) {
        Firestore db = Firebase.getFirestore("beat-75a88");
        CollectionReference colRef = db.collection("users");
        int userID = getMaxID(colRef, "userID") + 1;

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        //INSERT 
        User user = new User(userID, email, password, formattedDate, 0);
        ApiFuture<WriteResult> result = colRef.document(userID + "").set(user);

        try {
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
        return email.matches(emailRegex);
    }

    public boolean checkUser() {
        listUser = (ArrayList<User>) userDAO.getAll();
        if (listUser != null) {
            for (User user : listUser) {
                if (user.getEmail().equalsIgnoreCase(txtEmail2.getText())) {
                    return false;
                }
            }
        }
        return true;
    }

    public String validateFormSigup(String email, String password, String confirmPassword) {
        // Kiểm tra tính hợp lệ của email
        if (email.isEmpty()) {
            txtEmail2.requestFocus();
            return "Vui lòng nhập Email";
        } else if (!isValidEmail(email)) {
            txtEmail2.requestFocus();
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

        jPanel1 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        lblContinueAs = new javax.swing.JLabel();
        txtEmail2 = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        btnSignup = new beatalbumshop.componment.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPassword.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(82, 82, 82));
        txtPassword.setText("jPasswordField1");
        txtPassword.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
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

        txtEmail2.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtEmail2.setForeground(new java.awt.Color(82, 82, 82));
        txtEmail2.setText("Email");
        txtEmail2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        txtEmail2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtEmail2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmail2ActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Sign Up");

        lblLogin.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblLogin.setText("ALREADY HAVE AN ACCOUNT? LOG IN");
        lblLogin.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        lblLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
        });

        txtPassword2.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        txtPassword2.setForeground(new java.awt.Color(82, 82, 82));
        txtPassword2.setText("jPasswordField1");
        txtPassword2.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        txtPassword2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassword2ActionPerformed(evt);
            }
        });

        btnSignup.setBackground(new java.awt.Color(0, 0, 0));
        btnSignup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sign up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblContinueAs)
                        .addGap(133, 133, 133))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPassword2)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                            .addComponent(txtEmail2)
                            .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblLogin)
                        .addGap(81, 81, 81))))
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblTitle)
                .addGap(68, 68, 68)
                .addComponent(txtEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(lblLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblContinueAs)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void lblContinueAsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContinueAsMouseClicked
        dispose();
        new Main().setVisible(true);
    }//GEN-LAST:event_lblContinueAsMouseClicked

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        dispose();
        new LogIn().setVisible(true);
    }//GEN-LAST:event_lblLoginMouseClicked

    private void txtPassword2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword2ActionPerformed

    private void txtEmail2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmail2ActionPerformed

    }//GEN-LAST:event_txtEmail2ActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed

        String email = txtEmail2.getText();
        String password = new String(txtPassword.getPassword());
        String confirmpassword = new String(txtPassword2.getPassword());
        String errorMessage = validateFormSigup(email, password, confirmpassword);
        if (errorMessage == null) {
            if (checkUser()) {
                createUser(email, password);
//                JOptionPane.showMessageDialog(this, "Thành công");
                dispose();
                new LogIn().setVisible(true);
                MyDialog.display(0, "Đăng ký thành công!");
            } else {
                MyDialog.display(1, "Email đã tồn tại vui lòng thử lại");
            }

        } else {
            MyDialog.display(1, errorMessage);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblContinueAs;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtEmail2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword2;
    // End of variables declaration//GEN-END:variables
}
