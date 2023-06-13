/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package beatalbumshop;

import beatalbumshop.componment.MyButton;
import beatalbumshop.componment.MyDialog;
import beatalbumshop.componment.MyLabel;
import beatalbumshop.componment.MyTextField;
import beatalbumshop.dao.Firebase;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.internal.NonNull;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author conro
 */
public class OrderHistory extends javax.swing.JPanel {
    ArrayList<AddressBook> lOrderHistory = new ArrayList<>();
    Customer cus = (Customer) LoggedInUser.getCurrentUser();

    /**
     * Creates new form AddAddress
     */
    public OrderHistory() {
        initComponents();

        //check admin or staff
        if (LoggedInUser.isCustomer()) {

            pnlOrderHistory.setLayout(new BoxLayout(pnlOrderHistory, BoxLayout.Y_AXIS));
            pnlOrderHistory.setBorder(new EmptyBorder(20, 20, 20, 20));

            //loadAddress();

        }
    }
    public void loadAddress() {
        for (AddressBook orderhistory : lOrderHistory) {
            showOrderHistoryCard(orderhistory);
        }
    }
    
    public void showOrderHistoryCard(AddressBook addressbook) {
        JPanel pnl = new JPanel();

        pnl.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10); // Thiết lập khoảng cách lề trái và phải của ô là 10 pixel
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //pnl.setBorder(new EmptyBorder(20, 20, 20, 20));
//        pnl.setBackground(Color.WHITE);
//        pnl.setSize(900, 150);
//
        MyLabel lblAddressType = new MyLabel(addressbook.getAddressType());
        lblAddressType.setFont(new Font("Open Sans", 0, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        pnl.add(lblAddressType, gbc);

        MyLabel lblFullName = new MyLabel(addressbook.getFullName());
        lblFullName.setFont(new Font("Open Sans", 0, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        pnl.add(lblFullName, gbc);
//
        MyLabel lblAddress = new MyLabel(addressbook.getAddress());
        lblAddress.setFont(new Font("Open Sans", 0, 14));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        pnl.add(lblAddress, gbc);
//        pnl.add(lblAddress);
//
        MyLabel lblPhoneNumber = new MyLabel(addressbook.getPhoneNumber());
        lblPhoneNumber.setFont(new Font("Open Sans", 0, 14));
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        pnl.add(lblPhoneNumber, gbc);
//        pnl.add(lblPhoneNumber);
//
        MyButton btnEdit = new MyButton();
        btnEdit.setText("Edit");
        btnEdit.setFont(new Font("Open Sans", 0, 14));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        pnl.add(btnEdit, gbc);
//        pnl.add(btnEdit);
//
        MyButton btnDelete = new MyButton();
        btnDelete.setText("Delete");
        btnDelete.setFont(new Font("Open Sans", 0, 14));
        btnDelete.setBackground(new Color(215, 46, 46));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        pnl.add(btnDelete, gbc);
//        pnl.add(btnDelete);

        pnlOrderHistory.add(pnl);

        MouseAdapter showEditAddress = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel pnlTabContent = (JPanel) getParent();

                AddAddress addAddress = new AddAddress();

                addAddress.getLblTitle().setText("Edit Address");
                addAddress.getBtnSave().setText("UPDATE");

                addAddress.getTxtAddressType().setText(lblAddressType.getText());
                addAddress.getTxtAddress().setText(lblAddress.getText());
                addAddress.getTxtFullName().setText(lblFullName.getText());
                addAddress.getTxtPhoneNumber().setText(lblPhoneNumber.getText());

                addAddress.setOldAddressType(lblAddressType.getText());
                addAddress.setOldAddress(lblAddress.getText());
                addAddress.setOldFullName(lblFullName.getText());
                addAddress.setOldPhoneNumber(lblPhoneNumber.getText());

                pnlTabContent.add(addAddress, "addaddresspanel");
                CardLayout c = (CardLayout) pnlTabContent.getLayout();
                c.show(pnlTabContent, "addaddresspanel");

            }
        };
        btnEdit.addMouseListener(showEditAddress);

        MouseAdapter deleteAddress = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Map<String, Object> deleteAddressBookData = new HashMap<>();
                deleteAddressBookData.put("addressType", lblAddressType.getText());
                deleteAddressBookData.put("fullName", lblFullName.getText());
                deleteAddressBookData.put("address", lblAddress.getText());
                deleteAddressBookData.put("phoneNumber", lblPhoneNumber.getText());

                //Thực hiện cập nhật dữ liệu trong Firestore
                Firestore db = (Firestore) Firebase.getFirestore("beat-75a88");
                String id = String.valueOf(cus.getID());
                DocumentReference documentRef = db.collection("customers").document(id);

                documentRef.update("lAddressBook", FieldValue.arrayRemove(deleteAddressBookData)); // Xóa dữ liệu cũ

                MyDialog.display(0, "Delete Address Successfully");
                loadAddress();
            }
        };
        btnDelete.addMouseListener(deleteAddress);

    }


    public void backToAccount() {
        JPanel pnlTabContent = (JPanel) getParent();
        CardLayout c = (CardLayout) pnlTabContent.getLayout();
        c.show(pnlTabContent, "account");
//        Account acc = new Account();
//        acc.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnClose = new beatalbumshop.componment.MyButton();
        pnlOrderHistory = new javax.swing.JPanel();

        lblTitle.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Order History");

        btnClose.setBackground(null);
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/beatalbumshop/resources/images/icons/back.png"))); // NOI18N
        btnClose.setPreferredSize(new java.awt.Dimension(70, 50));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOrderHistoryLayout = new javax.swing.GroupLayout(pnlOrderHistory);
        pnlOrderHistory.setLayout(pnlOrderHistoryLayout);
        pnlOrderHistoryLayout.setHorizontalGroup(
            pnlOrderHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        pnlOrderHistoryLayout.setVerticalGroup(
            pnlOrderHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(pnlOrderHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOrderHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        backToAccount();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnClose;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlOrderHistory;
    // End of variables declaration//GEN-END:variables
}
