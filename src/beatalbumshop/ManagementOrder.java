package beatalbumshop;

import beatalbumshop.componment.MyButton;
import beatalbumshop.componment.MyDialog;
import beatalbumshop.componment.MyOkCancelDialog;
import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.dao.CustomerDAO;
import beatalbumshop.dao.CustomerDAOImpl;
import beatalbumshop.dao.OrderDAO;
import beatalbumshop.dao.OrderDAOImpl;
import beatalbumshop.dao.UserDAO;
import beatalbumshop.dao.UserDAOImpl;
import beatalbumshop.model.Album;
import beatalbumshop.model.Item;
import beatalbumshop.model.LoggedInUser;
import beatalbumshop.model.Order;
import beatalbumshop.model.OrderStatus;
import beatalbumshop.model.Track;
import beatalbumshop.model.User;
import beatalbumshop.utils.ClearComponent;
import beatalbumshop.utils.ImageHelper;
import beatalbumshop.utils.OtherHelper;
import beatalbumshop.utils.TimeHelper;
import beatalbumshop.utils.Validator;
import java.awt.CardLayout;
import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 * A panel that displays and manages the orders in the system.
 */
public class ManagementOrder extends javax.swing.JPanel {

    ArrayList<Order> lOrder = new ArrayList<>(); //List Order
    OrderDAO orderDAO = new OrderDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    
    ArrayList<Album> lAlbum = new ArrayList<>(); //List Album
    ArrayList<Track> lTrack = new ArrayList<>(); //List Album
    DefaultTableModel orderNotAssignedModel;
    DefaultTableModel orderAssignToMeModel;
    AlbumDAO albumDAO = new AlbumDAOImpl();
    int indexNotAssigned = -1;
    int indexAssignToMe = -1;
    User user;
    
    /**
     * Creates new form ManagementOrder.
     */
    public ManagementOrder() {
        initComponents();
        
        user = (User) LoggedInUser.getCurrentUser();
        
        if(LoggedInUser.isAdmin() && tbpOrder.isVisible()) {
            tbpOrder.remove(pnlAssignToMe);
            tbpOrder.setTitleAt(0, "All Order");
            btnTakeThisOrder.setVisible(false);
        }
        
        //table
        //tao model
        orderNotAssignedModel = new DefaultTableModel();
        orderAssignToMeModel = new DefaultTableModel();

        // Set the table model to the tblAlbum table
        tblOrderNotAssigned.setModel(orderNotAssignedModel);
        tblOrderAssignToMe.setModel(orderAssignToMeModel);
        
        //disable table editing
        tblOrderNotAssigned.setDefaultEditor(Object.class, null); 
        tblOrderAssignToMe.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"ID", "Full Name", "Date Created", "Quantity", "Total", "Status"};
        if(LoggedInUser.isAdmin()) {
            colNames = new String[] {"ID", "Full Name", "Date Created", "Quantity", "Total", "Status", "Assign to"};
        }
        orderNotAssignedModel.setColumnIdentifiers(colNames);
        orderAssignToMeModel.setColumnIdentifiers(colNames);

        //column width
        tblOrderNotAssigned.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblOrderNotAssigned.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblOrderNotAssigned.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblOrderNotAssigned.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblOrderNotAssigned.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblOrderNotAssigned.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        tblOrderAssignToMe.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblOrderAssignToMe.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblOrderAssignToMe.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblOrderAssignToMe.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblOrderAssignToMe.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblOrderAssignToMe.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        cboStatusFilterNotAssigned.removeAllItems();
        cboStatusFilterAssignToMe.removeAllItems();
        cboStatusFilterNotAssigned.setModel(new DefaultComboBoxModel<String>(OrderStatus.getStatusNames().toArray(new String[0])));
        cboStatusFilterAssignToMe.setModel(new DefaultComboBoxModel<String>(OrderStatus.getStatusNames().toArray(new String[0])));
        cboStatusFilterNotAssigned.insertItemAt("All", 0);
        cboStatusFilterAssignToMe.insertItemAt("All", 0);
        cboStatusFilterNotAssigned.setSelectedIndex(0);
        cboStatusFilterAssignToMe.setSelectedIndex(0);
        
        if(LoggedInUser.isStaff()) {
            cboStatusFilterNotAssigned.setVisible(false);
            lblCountNotAssigned.setVisible(false);
        }
        
        //table data
        fillToTableNotAssigned();
        fillToTableAssignToMe();
        
        tblOrderNotAssigned.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM

    /**
     * Selects the row at the specified index in the "Order Not Assigned" table.
     *
     * @param i The index of the row to select.
     */
    public void selectRowNotAssigned(int i) {
        if(i >= 0 && tblOrderNotAssigned.getRowCount() > 0) {
            indexNotAssigned = i;
            tblOrderNotAssigned.setRowSelectionInterval(indexNotAssigned, indexNotAssigned);

            //scroll toi dong duoc chon
            tblOrderNotAssigned.scrollRectToVisible(new Rectangle(tblOrderNotAssigned.getCellRect(indexNotAssigned, 0, true)));
        }
    } 
    
    /**
     * Selects the row at the specified index in the "Order Assign to Me" table.
     *
     * @param i The index of the row to select.
     */
    public void selectRowAssignToMe(int i) {
        if(i >= 0 && tblOrderAssignToMe.getRowCount() > 0) {
            indexAssignToMe = i;
            tblOrderAssignToMe.setRowSelectionInterval(indexAssignToMe, indexAssignToMe);

            //scroll toi dong duoc chon
            tblOrderAssignToMe.scrollRectToVisible(new Rectangle(tblOrderAssignToMe.getCellRect(indexAssignToMe, 0, true)));
        }
    } 
    
    
    
    /**
     * Fills the "Order Not Assigned" table with the orders from the database.
     */
    public void fillToTableNotAssigned() {
        lOrder = (ArrayList<Order>) orderDAO.getAll();
        
        orderNotAssignedModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lOrder != null) {
            int allCount = lOrder.size();
            int pendingCount = 0;
            int processingCount = 0;
            int shippedCount = 0;
            int deliveredCount = 0;
            int cancelledCount = 0;
            int returnedCount = 0;
            
            for(Order order : lOrder) {
                String status = OrderStatus.getStatusNameByLongValue(order.getStatus());
                double total = 0.0;
                int items = 0;
                for(Item item : order.getlOrderItem()) {
                    total += item.getQuantity() * (albumDAO.getByID(item.getAlbumID()).getPrice());
                    items += item.getQuantity();
                }
                
                if(LoggedInUser.isAdmin()) {
                    String selectedItem = cboStatusFilterNotAssigned.getSelectedItem().toString();
                    
                    if(selectedItem.equalsIgnoreCase("all")) {
                        String staffEmail = (userDAO.getByID(order.getStaffID()) == null) ? "" : userDAO.getByID(order.getStaffID()).getEmail();
                        orderNotAssignedModel.addRow(new Object[] {order.getOrderID(), order.getFullName(), order.getDateCreated(), items, total, status, staffEmail});
                    }
                    else {
                        long selectedStatus = OrderStatus.getLongValueByName(selectedItem);
                        if(order.getStatus() == selectedStatus) {
                            String staffEmail = (userDAO.getByID(order.getStaffID()) == null) ? "" : userDAO.getByID(order.getStaffID()).getEmail();
                            orderNotAssignedModel.addRow(new Object[] {order.getOrderID(), order.getFullName(), order.getDateCreated(), items, total, status, staffEmail});    
                        }
                    }
                    
                    if(order.getStatus() == OrderStatus.PENDING) pendingCount++;
                    else if(order.getStatus() == OrderStatus.PROCESSING) processingCount++;
                    else if(order.getStatus() == OrderStatus.SHIPPED) shippedCount++;
                    else if(order.getStatus() == OrderStatus.DELIVERED) deliveredCount++;
                    else if(order.getStatus() == OrderStatus.CANCELLED) cancelledCount++;
                    else if(order.getStatus() == OrderStatus.RETURNED) returnedCount++;
                }
                else if(order.getStaffID() == -1) {
                    orderNotAssignedModel.addRow(new Object[] {order.getOrderID(), order.getFullName(), order.getDateCreated(), items, total, status});
                }
            }
            
            lblCountNotAssigned.setText("<html>All: " + allCount + "<br>Pending: " + pendingCount + "<br>Processing: " + processingCount + "<br>Shipped: " + shippedCount + "<br>Delivered: " + deliveredCount + "<br>Cancelled: " + cancelledCount + "<br>Returned: " + returnedCount + "</html>");
        }
    }
    
    
    
    /**
     * Fills the "Order Assign to Me" table with the orders from the database.
     */
    public void fillToTableAssignToMe() {
        lOrder = (ArrayList<Order>) orderDAO.getAll();
        
        orderAssignToMeModel.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lOrder != null) {
            int allCount = 0;
            int pendingCount = 0;
            int processingCount = 0;
            int shippedCount = 0;
            int deliveredCount = 0;
            int cancelledCount = 0;
            int returnedCount = 0;
            
            for(Order order : lOrder) {
                String status = OrderStatus.getStatusNameByLongValue(order.getStatus());
                double total = 0.0;
                int items = 0;
                for(Item item : order.getlOrderItem()) {
                    total += item.getQuantity() * (albumDAO.getByID(item.getAlbumID()).getPrice());
                    items += item.getQuantity();
                }
                
                if(order.getStaffID() == user.getID()) {
                    String selectedItem = cboStatusFilterAssignToMe.getSelectedItem().toString();
                    
                    if(selectedItem.equalsIgnoreCase("all")) {
                        orderAssignToMeModel.addRow(new Object[] {order.getOrderID(), order.getFullName(), order.getDateCreated(), items, total, status});
                    }
                    else {
                        long selectedStatus = OrderStatus.getLongValueByName(selectedItem);
                        if(order.getStatus() == selectedStatus) {
                            orderAssignToMeModel.addRow(new Object[] {order.getOrderID(), order.getFullName(), order.getDateCreated(), items, total, status});
                        }
                    }
                    
                    allCount++;
                    if(order.getStatus() == OrderStatus.PENDING) pendingCount++;
                    else if(order.getStatus() == OrderStatus.PROCESSING) processingCount++;
                    else if(order.getStatus() == OrderStatus.SHIPPED) shippedCount++;
                    else if(order.getStatus() == OrderStatus.DELIVERED) deliveredCount++;
                    else if(order.getStatus() == OrderStatus.CANCELLED) cancelledCount++;
                    else if(order.getStatus() == OrderStatus.RETURNED) returnedCount++;
                }
            }
            
            lblCountAssignToMe.setText("<html>All: " + allCount + "<br>Pending: " + pendingCount + "<br>Processing: " + processingCount + "<br>Shipped: " + shippedCount + "<br>Delivered: " + deliveredCount + "<br>Cancelled: " + cancelledCount + "<br>Returned: " + returnedCount + "</html>");
        }
    }
    
    
    
    /**
     * Finds the index of the album with the given ID in the album list.
     *
     * @param albumID The ID of the album to find.
     * @return The index of the album, or -1 if not found.
     */
    public Integer findAlbumIndex(String albumID) {
        for(Album album : lAlbum) {
            if((album.getAlbumID()).equalsIgnoreCase(albumID)) {
                return lAlbum.indexOf(album);
            }
        }
        return -1;
    }
    
    
    
    /**
     * Shows the details of the order with the given ID in a separate panel.
     *
     * @param id The ID of the order to display.
     */
    public void showDetail(long id) {
        if(id == -1) {
            MyDialog.display(1, "Vui lòng chọn dòng cần xem");
            return;
        }
        
        OrderDetail tabOD = new OrderDetail(id);

        JPanel pnlTabContent = (JPanel) getParent();
        pnlTabContent.add(tabOD, "orderdetaildetailpanel");
        
        MainAdmin.showTab("orderdetaildetailpanel");
        
        tabOD.displayItemInOrder();
    }
    
    
    
    public void clearForm() {
//        index = -1;
//        tblOrder.getSelectionModel().clearSelection(); //bo chon tren table
//        ClearComponent.clear(lblImage, lblID, txtName, txtPrice, txtInStock);
//        albumImage = null;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpOrder = new javax.swing.JTabbedPane();
        pnlNotAssigned = new javax.swing.JPanel();
        cboStatusFilterNotAssigned = new beatalbumshop.componment.MyComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderNotAssigned = new beatalbumshop.componment.MyTable();
        btnViewDetailNotAssigned = new beatalbumshop.componment.MyButton();
        btnTakeThisOrder = new beatalbumshop.componment.MyButton();
        lblCountNotAssigned = new beatalbumshop.componment.MyLabel();
        pnlAssignToMe = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrderAssignToMe = new beatalbumshop.componment.MyTable();
        btnViewDetailAssignToMe = new beatalbumshop.componment.MyButton();
        cboStatusFilterAssignToMe = new beatalbumshop.componment.MyComboBox();
        lblCountAssignToMe = new beatalbumshop.componment.MyLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));
        setPreferredSize(new java.awt.Dimension(1030, 658));

        tbpOrder.setBackground(new java.awt.Color(255, 255, 255));
        tbpOrder.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N

        pnlNotAssigned.setBackground(new java.awt.Color(255, 255, 255));
        pnlNotAssigned.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        cboStatusFilterNotAssigned.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboStatusFilterNotAssigned.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboStatusFilterNotAssigned.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        cboStatusFilterNotAssigned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatusFilterNotAssignedActionPerformed(evt);
            }
        });

        tblOrderNotAssigned.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblOrderNotAssignedMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrderNotAssigned);

        btnViewDetailNotAssigned.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnViewDetailNotAssigned.setText("View Deatils");
        btnViewDetailNotAssigned.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewDetailNotAssigned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailNotAssignedActionPerformed(evt);
            }
        });

        btnTakeThisOrder.setBackground(new java.awt.Color(0, 162, 47));
        btnTakeThisOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 162, 47)));
        btnTakeThisOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnTakeThisOrder.setText("Take this Order");
        btnTakeThisOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTakeThisOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTakeThisOrderActionPerformed(evt);
            }
        });

        lblCountNotAssigned.setText("myLabel1");
        lblCountNotAssigned.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlNotAssignedLayout = new javax.swing.GroupLayout(pnlNotAssigned);
        pnlNotAssigned.setLayout(pnlNotAssignedLayout);
        pnlNotAssignedLayout.setHorizontalGroup(
            pnlNotAssignedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNotAssignedLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlNotAssignedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNotAssignedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnTakeThisOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnViewDetailNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboStatusFilterNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCountNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlNotAssignedLayout.setVerticalGroup(
            pnlNotAssignedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNotAssignedLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNotAssignedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNotAssignedLayout.createSequentialGroup()
                        .addComponent(btnViewDetailNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnTakeThisOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(cboStatusFilterNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCountNotAssigned, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tbpOrder.addTab("Not Assigned", pnlNotAssigned);

        pnlAssignToMe.setBackground(new java.awt.Color(255, 255, 255));
        pnlAssignToMe.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        tblOrderAssignToMe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblOrderAssignToMeMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblOrderAssignToMe);

        btnViewDetailAssignToMe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnViewDetailAssignToMe.setText("View Deatils");
        btnViewDetailAssignToMe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnViewDetailAssignToMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailAssignToMeActionPerformed(evt);
            }
        });

        cboStatusFilterAssignToMe.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboStatusFilterAssignToMe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboStatusFilterAssignToMe.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        cboStatusFilterAssignToMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatusFilterAssignToMeActionPerformed(evt);
            }
        });

        lblCountAssignToMe.setText("myLabel1");
        lblCountAssignToMe.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlAssignToMeLayout = new javax.swing.GroupLayout(pnlAssignToMe);
        pnlAssignToMe.setLayout(pnlAssignToMeLayout);
        pnlAssignToMeLayout.setHorizontalGroup(
            pnlAssignToMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAssignToMeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAssignToMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAssignToMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnViewDetailAssignToMe, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addComponent(cboStatusFilterAssignToMe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblCountAssignToMe, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlAssignToMeLayout.setVerticalGroup(
            pnlAssignToMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAssignToMeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlAssignToMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAssignToMeLayout.createSequentialGroup()
                        .addComponent(btnViewDetailAssignToMe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(cboStatusFilterAssignToMe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCountAssignToMe, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tbpOrder.addTab("Assign to Me", pnlAssignToMe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpOrder, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpOrder, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrderNotAssignedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderNotAssignedMousePressed
        selectRowNotAssigned(tblOrderNotAssigned.getSelectedRow());
    }//GEN-LAST:event_tblOrderNotAssignedMousePressed

    private void btnViewDetailNotAssignedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailNotAssignedActionPerformed
        if(tblOrderNotAssigned.getSelectedRow() == -1) {
            MyDialog.display(1, "Vui lòng chọn dòng cần xem");
            return;
        }
        
        long id = Long.parseLong(tblOrderNotAssigned.getValueAt(tblOrderNotAssigned.getSelectedRow(), 0).toString());
        showDetail(id);
    }//GEN-LAST:event_btnViewDetailNotAssignedActionPerformed

    private void btnTakeThisOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTakeThisOrderActionPerformed
        int row = tblOrderNotAssigned.getSelectedRow();
        if(row == -1) {
            MyDialog.display(1, "Vui lòng chọn dòng");
            return;
        }
        
        MyOkCancelDialog d = new MyOkCancelDialog(null, true, 2, "Are you sure you want to take this order?\nYou will be taking responsibility for this order, and the status will change to Processing.");
        d.setVisible(true);
        int returnStatus = d.getReturnStatus();

        if(returnStatus == MyOkCancelDialog.RET_OK) {
            long orderID = Long.parseLong(tblOrderNotAssigned.getValueAt(row, 0).toString());
            Order order = new Order();

            for(Order o : lOrder) {
                if(o.getOrderID() == orderID) {
                    order = o;
                }
            }
            
            //khong the take order da bi cancelled
            if(order.getStatus() == OrderStatus.CANCELLED) {
                MyDialog.display(1, "You are unable to take this order as it has been cancelled");
                return;
            }
            else {
                ArrayList<Album> lA = (ArrayList<Album>) albumDAO.getAll();
                ArrayList<Item> lI = (ArrayList<Item>) order.getlOrderItem();
                
                for(Item i : lI) {
                    for(Album a : lA) {
                        if(i.getAlbumID().equals(a.getAlbumID())) {
                            a.setInStock(a.getInStock() - i.getQuantity());
                            boolean re = albumDAO.add(a);
                            if(!re) {
                                MyDialog.display(1, "Có lỗi xảy ra khi cập nhật số lượng InStock");
                            }
                        }
                    }
                }
            }

            order.setStaffID(user.getID());
            order.setStatus(OrderStatus.PROCESSING);
            orderDAO.updateByID(order);
            
            fillToTableNotAssigned();
            fillToTableAssignToMe();
            
            // 1 dong cuoi
            if(tblOrderNotAssigned.getRowCount() == 0) {
                indexNotAssigned = -1;
                tblOrderNotAssigned.getSelectionModel().clearSelection();
            }
            //xoa dong cuoi
            else if(indexNotAssigned == tblOrderNotAssigned.getRowCount()) selectRowNotAssigned(indexNotAssigned - 1);
            else selectRowNotAssigned(indexNotAssigned);
        }
    }//GEN-LAST:event_btnTakeThisOrderActionPerformed

    private void tblOrderAssignToMeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderAssignToMeMousePressed
        selectRowAssignToMe(tblOrderAssignToMe.getSelectedRow());
    }//GEN-LAST:event_tblOrderAssignToMeMousePressed

    private void btnViewDetailAssignToMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailAssignToMeActionPerformed
        if(tblOrderAssignToMe.getSelectedRow() == -1) {
            MyDialog.display(1, "Vui lòng chọn dòng cần xem");
            return;
        }
        
        long id = Long.parseLong(tblOrderAssignToMe.getValueAt(tblOrderAssignToMe.getSelectedRow(), 0).toString());
        showDetail(id);
    }//GEN-LAST:event_btnViewDetailAssignToMeActionPerformed

    private void cboStatusFilterNotAssignedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusFilterNotAssignedActionPerformed
        if(cboStatusFilterNotAssigned.getSelectedItem() != null) {
            fillToTableNotAssigned();
        }
    }//GEN-LAST:event_cboStatusFilterNotAssignedActionPerformed

    private void cboStatusFilterAssignToMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusFilterAssignToMeActionPerformed
        if(cboStatusFilterAssignToMe.getSelectedItem() != null) {
            fillToTableAssignToMe();
        }
    }//GEN-LAST:event_cboStatusFilterAssignToMeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnTakeThisOrder;
    private beatalbumshop.componment.MyButton btnViewDetailAssignToMe;
    private beatalbumshop.componment.MyButton btnViewDetailNotAssigned;
    private beatalbumshop.componment.MyComboBox cboStatusFilterAssignToMe;
    private beatalbumshop.componment.MyComboBox cboStatusFilterNotAssigned;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private beatalbumshop.componment.MyLabel lblCountAssignToMe;
    private beatalbumshop.componment.MyLabel lblCountNotAssigned;
    private javax.swing.JPanel pnlAssignToMe;
    private javax.swing.JPanel pnlNotAssigned;
    private beatalbumshop.componment.MyTable tblOrderAssignToMe;
    private beatalbumshop.componment.MyTable tblOrderNotAssigned;
    private javax.swing.JTabbedPane tbpOrder;
    // End of variables declaration//GEN-END:variables
}
