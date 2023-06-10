package beatalbumshop;

import beatalbumshop.componment.MyDialog;
import beatalbumshop.dao.CustomerDAO;
import beatalbumshop.dao.CustomerDAOImpl;
import beatalbumshop.dao.Firebase;
import beatalbumshop.dao.UserDAO;
import beatalbumshop.dao.UserDAOImpl;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Customer;
import beatalbumshop.model.Track;
import beatalbumshop.utils.ClearComponent;
import beatalbumshop.utils.ImageHelper;
import beatalbumshop.utils.TextHelper;
import beatalbumshop.utils.TimeHelper;
import beatalbumshop.utils.Validator;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManagementCustomer extends javax.swing.JPanel {

    ArrayList<Customer> lCustomer = new ArrayList<>(); //List Customer
    ArrayList<AddressBook> lAddressBook = new ArrayList<>(); //List AddressBook
    DefaultTableModel customerModel;
    DefaultTableModel addressbookModel;
    CustomerDAO customerDAO = new CustomerDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    int index = -1;
    
    public ManagementCustomer() {
        initComponents();
        
        //table
        //tao model
        customerModel = new DefaultTableModel();
        addressbookModel = new DefaultTableModel();

        // Set the table model to the tblCustomer table
        tblCustomer.setModel(customerModel);
        tblAddressBook.setModel(addressbookModel);
        
        //disable table editing
        tblCustomer.setDefaultEditor(Object.class, null); 
        tblAddressBook.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"ID", "Email", "Password", "DateCreated"};
        customerModel.setColumnIdentifiers(colNames);
        
        String [] colNames2 = {"ID", "Type", "Full Name", "Address", "PhoneNumber"};
        addressbookModel.setColumnIdentifiers(colNames2);

        //column width
        tblCustomer.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblCustomer.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblCustomer.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblCustomer.getColumnModel().getColumn(3).setPreferredWidth(130);
        
        tblAddressBook.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblAddressBook.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblAddressBook.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblAddressBook.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblAddressBook.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        //table data
        fillToTable();
        
        tblCustomer.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM

    public void selectRow(int i) {
        if(i >= 0 && tblCustomer.getRowCount() > 0) {
            index = i;
            tblCustomer.setRowSelectionInterval(index, index);
            showDetail();
            //scroll toi dong duoc chon
            tblCustomer.scrollRectToVisible(new Rectangle(tblCustomer.getCellRect(index, 0, true)));
            
            txtEmail.setEditable(false);
            txtPassword.setEditable(false);
        }
    } 
    
    
    
    public void fillToTable() {
        lCustomer = (ArrayList<Customer>) customerDAO.getAll();
        
        customerModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lCustomer != null) {
            for(Customer customer : lCustomer) {
                customerModel.addRow(new Object[] {customer.getID(), customer.getEmail(), customer.getPassword(), customer.getDateCreated()});
            }
        }
    }
    
    
    
    public void fillToAddressBook(ArrayList<AddressBook> list) {
        lAddressBook = list;
        
        addressbookModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lAddressBook != null) {
            for(AddressBook addressbook : lAddressBook) {
                addressbookModel.addRow(new Object[] {addressbook.getAddressBoookID(), addressbook.getAddressType(), addressbook.getFullName(), addressbook.getAddress(), addressbook.getPhoneNumber()});
            }
        }
    }
    
    
    
    public Integer findCustomerIndex(long customerID) {
        for(Customer customer : lCustomer) {
            if(customer.getID() == customerID) {
                return lCustomer.indexOf(customer);
            }
        }
        return -1;
    }
    
    
    
    public void showDetail() {
        Customer customer = new Customer();
        
        //lay ID trong cot dau tien cua hang duoc chon
        String idS = tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0).toString();
        long id = 0;
        try {
            id = Long.parseLong(idS);
        } catch(Exception ex) {
            MyDialog.display(1, "ID phải là số nguyên");
            ex.printStackTrace();
        }
        
        //tim customer co ID trong lCustomer
        customer = lCustomer.get(findCustomerIndex(id));
        
        //do du lieu tu Customer customer len form
        lblID.setText(customer.getID()+ "");
        txtEmail.setText(customer.getEmail());
        txtPassword.setText(customer.getPassword());
        txtDateCreated.setText(customer.getDateCreated());
        fillToAddressBook(customer.getlAddressBook());
    }
    
    
    
    public void clearForm() {
        index = -1;
        tblCustomer.getSelectionModel().clearSelection(); //bo chon tren table
        tblAddressBook.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(lblID, txtEmail, txtPassword, txtDateCreated);
    }
    
    
    
    public long getMaxID() {
        long id = 1;
        for(int i = 0; i < tblCustomer.getRowCount(); i++) {
            if(Long.parseLong(tblCustomer.getValueAt(i, 0).toString()) > id) {
                id = Long.parseLong(tblCustomer.getValueAt(i, 0).toString());
            }
        }
        
        return id;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new beatalbumshop.componment.MyTable();
        lblID2 = new beatalbumshop.componment.MyLabel();
        lblEmail = new beatalbumshop.componment.MyLabel();
        txtEmail = new beatalbumshop.componment.MyTextField();
        btnDelete = new beatalbumshop.componment.MyButton();
        lblID = new beatalbumshop.componment.MyLabel();
        btnAdd = new beatalbumshop.componment.MyButton();
        lblPassword = new beatalbumshop.componment.MyLabel();
        txtPassword = new beatalbumshop.componment.MyTextField();
        lblDateCreated = new beatalbumshop.componment.MyLabel();
        txtDateCreated = new beatalbumshop.componment.MyTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAddressBook = new beatalbumshop.componment.MyTable();
        btnNew = new beatalbumshop.componment.MyButton();
        btnUpdate = new beatalbumshop.componment.MyButton();
        lblAddressType2 = new beatalbumshop.componment.MyLabel();
        lblFullName2 = new beatalbumshop.componment.MyLabel();
        lblPhoneNumber2 = new beatalbumshop.componment.MyLabel();
        lblAddress2 = new beatalbumshop.componment.MyLabel();
        lblAddressType = new beatalbumshop.componment.MyLabel();
        lblFullName = new beatalbumshop.componment.MyLabel();
        lblPhoneNumber = new beatalbumshop.componment.MyLabel();
        lblAddress = new beatalbumshop.componment.MyLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));
        setPreferredSize(new java.awt.Dimension(1030, 658));

        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCustomerMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomer);

        lblID2.setForeground(new java.awt.Color(80, 80, 80));
        lblID2.setText("ID");

        lblEmail.setForeground(new java.awt.Color(80, 80, 80));
        lblEmail.setText("Email");

        txtEmail.setEditable(false);
        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtEmail.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtEmail.setNextFocusableComponent(txtPassword);

        btnDelete.setBackground(new java.awt.Color(215, 46, 46));
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 46, 46)));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblID.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        btnAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAdd.setText("Add");
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblPassword.setForeground(new java.awt.Color(80, 80, 80));
        lblPassword.setText("Password");

        txtPassword.setEditable(false);
        txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtPassword.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        txtPassword.setNextFocusableComponent(txtDateCreated);

        lblDateCreated.setForeground(new java.awt.Color(80, 80, 80));
        lblDateCreated.setText("D.Created");

        txtDateCreated.setEditable(false);
        txtDateCreated.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtDateCreated.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        tblAddressBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAddressBookMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblAddressBook);

        btnNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNew.setText("New");
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnUpdate.setText("Update");
        btnUpdate.setEnabled(false);
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        lblAddressType2.setForeground(new java.awt.Color(80, 80, 80));
        lblAddressType2.setText("Type");

        lblFullName2.setForeground(new java.awt.Color(80, 80, 80));
        lblFullName2.setText("Full Name");

        lblPhoneNumber2.setForeground(new java.awt.Color(80, 80, 80));
        lblPhoneNumber2.setText("P.Number");

        lblAddress2.setForeground(new java.awt.Color(80, 80, 80));
        lblAddress2.setText("Address");

        lblAddressType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFullName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAddress.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDateCreated, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(450, 450, 450)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAddressType, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                            .addComponent(lblFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddressType2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFullName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblAddressType2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblAddressType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(15, 15, 15)
                                        .addComponent(lblFullName2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(lblPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblCustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMousePressed
        selectRow(tblCustomer.getSelectedRow());
    }//GEN-LAST:event_tblCustomerMousePressed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String id = lblID.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String dateCreated = TimeHelper.getCurrentDateTime();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

        errors.add((!Validator.isNotNull((JTextField)txtPassword, password)) ? "Vui lòng nhập Password\n" : "");
        errors.add((!Validator.isNotNull((JTextField)txtEmail, email)) ? "Vui lòng nhập Email\n" : "");

        if(id == null) errors.add("Vui lòng nhập ID");
        
        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        else {
            // kiem tra ton tai
            if (customerDAO.checkExitByEmail(email) && userDAO.checkExitByEmail(email)) {
                Firestore db = Firebase.getFirestore("beat-75a88");
                CollectionReference colRef = db.collection("customers");
                
                // hash password
                password = TextHelper.HashPassword(password);

                long idL = getMaxID() + 1;
                boolean result = customerDAO.add(new Customer(lAddressBook, null, idL, email, password));
                if(result) {
                    //them thanh cong
                    fillToTable();
                    selectRow(findCustomerIndex(idL));
                }
                else {
                    //them that bai
                    MyDialog.display(1, "Có lỗi xảy ra.");
                }
            }
            else {
                txtEmail.requestFocus();
                MyDialog.display(1, "Email đã tồn tại vui lòng thử lại");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //delete
        String id = tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0).toString();
 
        boolean result = customerDAO.deleteByID(id);
        
        if(result) {
            //delete thanh cong
            fillToTable();
            
            //xoa 1 dong cuoi
            if(lCustomer.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lCustomer.size()) selectRow(index - 1);
            else selectRow(index);
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
        txtEmail.requestFocus();
        txtEmail.setEditable(true);
        txtPassword.setEditable(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
//        String email = txtEmail.getText();
//        String password = txtPassword.getText();
//
//        //validate
//        ArrayList<String> errors = new ArrayList<>();
//
//        errors.add((!Validator.isNotNull((JTextField) txtEmail, email)) ? "Vui lòng nhập Email\n" : "");
//        errors.add((!Validator.isNotNull((JTextField) txtPassword, password)) ? "Vui lòng nhập Password\n" : "");
//
//        Collections.reverse(errors);
//
//        String e = "";
//        for (String s : errors) {
//            e += s;
//        }
//
//        //co loi
//        if (!e.isEmpty()) {
//            MyDialog.display(1, e);
//            return;
//        }
//
//        //update
//        long id = Long.parseLong(tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0).toString());
//        boolean result = customerDAO.updateByID(new Customer(lAddressBook, id, email, password));
//
//        if (result) {
//            //update thanh cong
//            fillToTable();
//            selectRow(findCustomerIndex(id));
//        } else {
//            //update that bai
//            MyDialog.display(1, "Có lỗi xảy ra.");
//        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblAddressBookMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAddressBookMousePressed
        int row = tblAddressBook.getSelectedRow();
        lblAddressType.setText(tblAddressBook.getValueAt(row, 1).toString());
        lblFullName.setText(tblAddressBook.getValueAt(row, 2).toString());
        lblPhoneNumber.setText(tblAddressBook.getValueAt(row, 4).toString());
        lblAddress.setText(tblAddressBook.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tblAddressBookMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnAdd;
    private beatalbumshop.componment.MyButton btnDelete;
    private beatalbumshop.componment.MyButton btnNew;
    private beatalbumshop.componment.MyButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private beatalbumshop.componment.MyLabel lblAddress;
    private beatalbumshop.componment.MyLabel lblAddress2;
    private beatalbumshop.componment.MyLabel lblAddressType;
    private beatalbumshop.componment.MyLabel lblAddressType2;
    private beatalbumshop.componment.MyLabel lblDateCreated;
    private beatalbumshop.componment.MyLabel lblEmail;
    private beatalbumshop.componment.MyLabel lblFullName;
    private beatalbumshop.componment.MyLabel lblFullName2;
    private beatalbumshop.componment.MyLabel lblID;
    private beatalbumshop.componment.MyLabel lblID2;
    private beatalbumshop.componment.MyLabel lblPassword;
    private beatalbumshop.componment.MyLabel lblPhoneNumber;
    private beatalbumshop.componment.MyLabel lblPhoneNumber2;
    private beatalbumshop.componment.MyTable tblAddressBook;
    private beatalbumshop.componment.MyTable tblCustomer;
    private beatalbumshop.componment.MyTextField txtDateCreated;
    private beatalbumshop.componment.MyTextField txtEmail;
    private beatalbumshop.componment.MyTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
