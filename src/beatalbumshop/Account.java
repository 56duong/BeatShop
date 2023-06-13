package beatalbumshop;

import Sanh.Cell.TableActionCellEditor;
import Sanh.Cell.TableActionCellRender;
import Sanh.Cell.TableActionEvent;
import beatalbumshop.componment.MyButton;
import beatalbumshop.componment.MyDialog;
import beatalbumshop.componment.MyLabel;
import beatalbumshop.dao.AddressBookDAO;
import beatalbumshop.dao.AddressBookDAOImpl;
import beatalbumshop.dao.Firebase;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Album;
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import beatalbumshop.utils.ImageHelper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class Account extends javax.swing.JPanel {

    Customer cus;
    ArrayList<AddressBook> lAddressBook = new ArrayList<>();
    AddressBookDAO addressbookDAO = new AddressBookDAOImpl();
    DefaultTableModel addressbookModel;

    public Account() {
        initComponents();

        //check admin or staff
        if (LoggedInUser.isCustomer()) {
            cus = (Customer) LoggedInUser.getCurrentUser();

            addressbookModel = (DefaultTableModel) tblAddressBook.getModel();

            //table header
            String[] colNames2 = {"Address Type", "Address", "Full Name", "PhoneNumber", "Action"};
            addressbookModel.setColumnIdentifiers(colNames2);

            // Set the table model to the tblCustomer table
            tblAddressBook.setModel(addressbookModel);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    displayUserInfo();
                    //super.componentShown(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }

            });

            //disable table editing
            tblAddressBook.setDefaultEditor(Object.class, null);
            // Tắt hiển thị đường kẻ viền dọc và ngang
            tblAddressBook.setShowGrid(false);
            tblAddressBook.setShowVerticalLines(false);
            tblAddressBook.setShowHorizontalLines(false);

            //column width
            tblAddressBook.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblAddressBook.getColumnModel().getColumn(1).setPreferredWidth(300);
            tblAddressBook.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblAddressBook.getColumnModel().getColumn(3).setPreferredWidth(130);
            tblAddressBook.getColumnModel().getColumn(4).setPreferredWidth(130);

            TableActionEvent event = new TableActionEvent() {
                @Override
                public void onEdit(int row) {
                    JPanel pnlTabContent = (JPanel) getParent();

                    AddAddress addAddress = new AddAddress();
                    addAddress.getLblTitle().setText("Edit Address");
                    addAddress.getBtnSave().setText("UPDATE");

                    addAddress.getTxtAddressType().setText(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 0).toString());
                    addAddress.getTxtAddress().setText(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 1).toString());
                    addAddress.getTxtFullName().setText(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 2).toString());
                    addAddress.getTxtPhoneNumber().setText(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 3).toString());

                    addAddress.setOldAddressType(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 0).toString());
                    addAddress.setOldAddress(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 1).toString());
                    addAddress.setOldFullName(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 2).toString());
                    addAddress.setOldPhoneNumber(tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 3).toString());

                    pnlTabContent.add(addAddress, "addaddresspanel");
                    CardLayout c = (CardLayout) pnlTabContent.getLayout();
                    c.show(pnlTabContent, "addaddresspanel");
                }

                @Override
                public void onDelete(int row) {
                    if (tblAddressBook.isEditing()) {
                        tblAddressBook.getCellEditor().stopCellEditing();
                    }
                    Map<String, Object> deleteAddressBookData = new HashMap<>();
                    deleteAddressBookData.put("addressType", tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 0).toString());
                    deleteAddressBookData.put("address", tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 1).toString());
                    deleteAddressBookData.put("fullName", tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 2).toString());
                    deleteAddressBookData.put("phoneNumber", tblAddressBook.getValueAt(tblAddressBook.getSelectedRow(), 3).toString());

                    //Thực hiện cập nhật dữ liệu trong Firestore
                    Firestore db = (Firestore) Firebase.getFirestore("beat-75a88");
                    String id = String.valueOf(cus.getID());
                    DocumentReference documentRef = db.collection("customers").document(id);

                    documentRef.update("lAddressBook", FieldValue.arrayRemove(deleteAddressBookData)); // Xóa dữ liệu cũ

                    MyDialog.display(0, "Delete Address Successfully");

                    DefaultTableModel model = (DefaultTableModel) tblAddressBook.getModel();
                    model.removeRow(row);
                    
                }

                @Override
                public void onView(int row) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };

            tblAddressBook.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
            tblAddressBook.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));

            //displayUserInfo();
        }
    }

    private void displayUserInfo() {

        // Get the currently logged-in user
        // Display user information
        txtEmail.setText(cus.getEmail());
        txtPassword.setText(cus.getPassword());
        txtDateCreated.setText(cus.getDateCreated());

        lAddressBook = (ArrayList<AddressBook>) addressbookDAO.getAll();

        addressbookModel.setRowCount(0); // Xóa các hàng trong bảng

        if (lAddressBook != null) {
            for (AddressBook address : lAddressBook) {

                // Thêm hàng vào bảng với các cột và các button tương ứng
                addressbookModel.addRow(new Object[]{
                    address.getAddressType(),
                    address.getAddress(),
                    address.getFullName(),
                    address.getPhoneNumber()
                });
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblID2 = new beatalbumshop.componment.MyLabel();
        lblName = new beatalbumshop.componment.MyLabel();
        txtEmail = new beatalbumshop.componment.MyTextField();
        lblArtist = new beatalbumshop.componment.MyLabel();
        lblName2 = new beatalbumshop.componment.MyLabel();
        txtDateCreated = new beatalbumshop.componment.MyTextField();
        txtPassword = new beatalbumshop.componment.MyPasswordField();
        btnChange = new beatalbumshop.componment.MyButton();
        txtLogOut = new beatalbumshop.componment.MyButton();
        btnNew = new beatalbumshop.componment.MyButton();
        pnlListAddressBook = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAddressBook = new beatalbumshop.componment.MyTable();
        btnOrderh = new beatalbumshop.componment.MyButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));
        setPreferredSize(new java.awt.Dimension(1030, 658));

        lblID2.setForeground(new java.awt.Color(80, 80, 80));
        lblID2.setText("My Account");

        lblName.setForeground(new java.awt.Color(80, 80, 80));
        lblName.setText("Email");

        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtEmail.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblArtist.setForeground(new java.awt.Color(80, 80, 80));
        lblArtist.setText("Password");

        lblName2.setForeground(new java.awt.Color(80, 80, 80));
        lblName2.setText("Date Created");

        txtDateCreated.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtDateCreated.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        txtPassword.setText("myPasswordField1");

        btnChange.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnChange.setText("Change");
        btnChange.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        txtLogOut.setBackground(new java.awt.Color(0, 162, 47));
        txtLogOut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 162, 47)));
        txtLogOut.setForeground(new java.awt.Color(255, 255, 255));
        txtLogOut.setText("Log Out");
        txtLogOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        txtLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLogOutActionPerformed(evt);
            }
        });

        btnNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNew.setText("+ New Address");
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        pnlListAddressBook.setPreferredSize(new java.awt.Dimension(946, 392));

        tblAddressBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblAddressBook.setRowHeight(40);
        jScrollPane1.setViewportView(tblAddressBook);

        javax.swing.GroupLayout pnlListAddressBookLayout = new javax.swing.GroupLayout(pnlListAddressBook);
        pnlListAddressBook.setLayout(pnlListAddressBookLayout);
        pnlListAddressBookLayout.setHorizontalGroup(
            pnlListAddressBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
        );
        pnlListAddressBookLayout.setVerticalGroup(
            pnlListAddressBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        btnOrderh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnOrderh.setText("Order History");
        btnOrderh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOrderh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlListAddressBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                        .addComponent(txtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnOrderh, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOrderh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblArtist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(pnlListAddressBook, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
//        new ChangePassword().setVisible(true);
        JPanel pnlTabContent = (JPanel) getParent();
        ChangePassword changePass = new ChangePassword();

        pnlTabContent.add(changePass, "changepasspanel");

        CardLayout c = (CardLayout) pnlTabContent.getLayout();
        c.show(pnlTabContent, "changepasspanel");

    }//GEN-LAST:event_btnChangeActionPerformed

    private void txtLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLogOutActionPerformed
        //closing all
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }
        LoggedInUser.logOut();
        new LogIn().setVisible(true);
    }//GEN-LAST:event_txtLogOutActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        JPanel pnlTabContent = (JPanel) getParent();
        AddAddress addAddress = new AddAddress();
        addAddress.getBtnSave().setText("ADD");
        pnlTabContent.add(addAddress, "addaddresspanel");

        CardLayout c = (CardLayout) pnlTabContent.getLayout();
        c.show(pnlTabContent, "addaddresspanel");
        addAddress.getLblTitle().setText("New Address");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnOrderhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderhActionPerformed
        // TODO add your handling code here:
        JPanel pnlTabContent = (JPanel) getParent();
        OrderHistory orderHistory = new OrderHistory();

        pnlTabContent.add(orderHistory, "orderHistory");
        CardLayout c = (CardLayout) pnlTabContent.getLayout();
        c.show(pnlTabContent, "orderHistory");
    }//GEN-LAST:event_btnOrderhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnChange;
    private beatalbumshop.componment.MyButton btnNew;
    private beatalbumshop.componment.MyButton btnOrderh;
    private javax.swing.JScrollPane jScrollPane1;
    private beatalbumshop.componment.MyLabel lblArtist;
    private beatalbumshop.componment.MyLabel lblID2;
    private beatalbumshop.componment.MyLabel lblName;
    private beatalbumshop.componment.MyLabel lblName2;
    private javax.swing.JPanel pnlListAddressBook;
    private beatalbumshop.componment.MyTable tblAddressBook;
    private beatalbumshop.componment.MyTextField txtDateCreated;
    private beatalbumshop.componment.MyTextField txtEmail;
    private beatalbumshop.componment.MyButton txtLogOut;
    private beatalbumshop.componment.MyPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
