package beatalbumshop;

import beatalbumshop.componment.MyComboBox;
import beatalbumshop.componment.MyDialog;
import beatalbumshop.componment.MyLabel;
import beatalbumshop.componment.MyNotification;
import beatalbumshop.componment.MyTextArea;
import beatalbumshop.componment.MyTextField;
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
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import beatalbumshop.model.Order;
import beatalbumshop.model.OrderStatus;
import beatalbumshop.model.User;
import beatalbumshop.utils.OtherHelper;
import beatalbumshop.utils.SendEmail;
import beatalbumshop.utils.Validator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class OrderDetail extends javax.swing.JPanel {

    OrderDAO orderDAO = new OrderDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    AlbumDAO albumDAO = new AlbumDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    Order order;
    User user;
    Customer customer;
    Component[] lC;
    ArrayList<ItemInOrder> lItemInOrder = new ArrayList<>();
    List<Album> lProduct = new ArrayList<>();
    
    public OrderDetail(long id) {
        initComponents();
        
        pnlListOrderItem.setLayout(new BoxLayout(pnlListOrderItem, BoxLayout.Y_AXIS));
        
        lC = new Component[] {txaMessage, txtFullName, txtPhoneNumber, txaAddress};
        
        order = orderDAO.getByID(id);
        user = userDAO.getByID(order.getStaffID());
        customer = customerDAO.getByID(order.getCustomerID());
        
        lblTitleOrderID.setText("Order #" + order.getOrderID());
        
        List<String> lS = new ArrayList<String>(); 
        for(User u : userDAO.getAll()) {
            if(u.getRole() == 0) {
                lS.add(u.getEmail());
            }
        }
        cboTitleStaffEmail.setModel(new DefaultComboBoxModel<String>(lS.toArray(new String[0])));
        if(user == null) { //chua assign
            cboTitleStaffEmail.setRenderer(new MyComboBox.MyComboBoxRenderer("ASSIGN TO"));
            cboTitleStaffEmail.setSelectedIndex(-1); //By default it selects first item, we don't want any selection
            
            lblAssignTo.setVisible(false);
            
            if(LoggedInUser.isStaff()) { //chua assign && isStaff
                btnEdit.setVisible(false);
            }
            else {
                lblAssignTo.setVisible(true);
            }
        }
        else if(user != null) { //da assign       
            cboTitleStaffEmail.setSelectedItem(user.getEmail());
            lblAssignTo.setVisible(true);
        }
        
        lblDateCreated.setText(order.getDateCreated());
        
        lblCustomerID.setText(customer.getID() + "");
        lblCustomerEmail.setText(customer.getEmail());
        
        lblOrderID.setText(order.getOrderID() + "");
        lblPaymentOption.setText(OtherHelper.paymentOptionToString(order.getPaymentOption()));
        txaMessage.setText(order.getMessage());
        
        txtFullName.setText(order.getFullName());
        txtPhoneNumber.setText(order.getPhoneNumber());
        txaAddress.setText(order.getAddress());
        
        cboStatus.removeAllItems();
        
        //chi cancelled khi pending || processing
        if(order.getStatus() == OrderStatus.PENDING) {
            lblStatusIcon.setBackground(new Color(131, 192, 213));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.PENDING));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.PROCESSING));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.CANCELLED));
        }
        else if(order.getStatus() == OrderStatus.PROCESSING) {
            lblStatusIcon.setBackground(new Color(38, 111, 183));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.PROCESSING));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.SHIPPED));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.CANCELLED));
        }
        else if(order.getStatus() == OrderStatus.SHIPPED) {
            lblStatusIcon.setBackground(new Color(38, 190, 90));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.SHIPPED));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.DELIVERED));
        }
        else if(order.getStatus() == OrderStatus.DELIVERED) {
            lblStatusIcon.setBackground(new Color(0, 153, 39));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.DELIVERED));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.RETURNED));
        }
        else if(order.getStatus() == OrderStatus.CANCELLED) {
            lblStatusIcon.setBackground(new Color(228, 89, 89));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.CANCELLED));
        }
        else if(order.getStatus() == OrderStatus.RETURNED) {
            lblStatusIcon.setBackground(new Color(228, 89, 89));
            cboStatus.addItem(OrderStatus.getStatusNameByLongValue(OrderStatus.RETURNED));
        }
        
        cboStatus.setSelectedItem(OrderStatus.getStatusNameByLongValue(order.getStatus()));
        
        cboProductList.removeAllItems();
        List<Album> lAllProduct = albumDAO.getAll(); 
        for(Album a : lAllProduct) {
            if(a.getInStock() > 0) {
                lProduct.add(a);
                cboProductList.addItem(a.getAlbumName() + "|" + a.getArtist() + "|$" + a.getPrice());
            }
        }
        
        enableField(false);
        displayItemInOrder();
    }
    
    
    
    public void enableField(boolean isEnable) {
        for(Component c : lC) {
            if(isEnable) {
                cboStatus.setEnabled(true);
                if(LoggedInUser.isAdmin() && (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PROCESSING)) {
                    cboTitleStaffEmail.setEnabled(true); // cap quyen assign khi isAdmin && (status = pending || processing)
                }
                
                if(order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PROCESSING) {
                    if(c instanceof MyTextField) {
                        ((MyTextField) c).setEnabled(true);
                        ((MyTextField) c).setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(2, 3, 2, 3)));
                    }
                    else if(c instanceof MyTextArea) {
                        ((MyTextArea) c).setEnabled(true);
                        ((MyTextArea) c).setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(2, 3, 2, 3)));
                    }

                    cboProductList.setVisible(true);
                    cboQuantity.setVisible(true);
                    btnAddProdcut.setVisible(true);
                }
            }
            else {
                cboStatus.setEnabled(false);
                cboTitleStaffEmail.setEnabled(false);
                    
                if(c instanceof MyTextField) {
                    ((MyTextField) c).setEnabled(false);
                    ((MyTextField) c).setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 1), new EmptyBorder(2, 3, 2, 3)));
                }
                else if(c instanceof MyTextArea) {
                    ((MyTextArea) c).setEnabled(false);
                    ((MyTextArea) c).setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 1), new EmptyBorder(2, 3, 2, 3)));
                }
                
                cboProductList.setVisible(false);
                cboQuantity.setVisible(false);
                btnAddProdcut.setVisible(false);
            }
        }
        
        for(ItemInOrder item : lItemInOrder) {
            if(isEnable) {
                if(order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PROCESSING) {
                    item.getBtnRemove().setVisible(true);
                    item.getCboQuantity().setEnabled(true);
                }
            }
            else {
                item.getBtnRemove().setVisible(false);
                item.getCboQuantity().setEnabled(false);
            }
        }
        
    }
    
    
    
    public void displayItemInOrder() {
        double subtotal = 0;
        pnlListOrderItem.removeAll();

        ArrayList<Item> lItem = new ArrayList<>();
        lItem = order.getlOrderItem();
              
        // empty
        if(lItem.isEmpty()) {
            pnlListOrderItem.setLayout(new BorderLayout());
            MyLabel lbl = new MyLabel("Currently Empty");
            lbl.setHorizontalAlignment(JLabel.CENTER);
            pnlListOrderItem.add(lbl);
        }
        else {
            for(Item item : lItem) {
                Album album = albumDAO.getByID(item.getAlbumID());
                
                if(album.getInStock() > 0) {
                    ItemInOrder sp = new ItemInOrder(this, order, album, item.getQuantity());

                    if(btnEdit.getText().equalsIgnoreCase("edit")) {
                        sp.getBtnRemove().setVisible(false);
                        sp.getCboQuantity().setEnabled(false);
                    }
                    
                    lItemInOrder.add(sp);
                    pnlListOrderItem.add(sp);
                    subtotal += sp.getSubtotal();
                }
            }
            
            pnlListOrderItem.revalidate();
            pnlListOrderItem.repaint();
        }
        
        lblSubtotal.setText("$ " + subtotal);
        String shipping = lblShipping.getText().substring(lblShipping.getText().indexOf("$ ") + 2);
        double total = subtotal + Double.parseDouble(shipping);
        lblTotal.setText("$ " + total);
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
        pnlContent = new javax.swing.JPanel();
        pnlCustomer = new javax.swing.JPanel();
        lblHeaderCustomer = new beatalbumshop.componment.MyLabel();
        lblCustomerID2 = new beatalbumshop.componment.MyLabel();
        lblCustomerID = new beatalbumshop.componment.MyLabel();
        lblCustomerEmail = new beatalbumshop.componment.MyLabel();
        lblCustomerEmail2 = new beatalbumshop.componment.MyLabel();
        pnlOrderDetails = new javax.swing.JPanel();
        lblHeaderOrderDetails = new beatalbumshop.componment.MyLabel();
        lblOrderID2 = new beatalbumshop.componment.MyLabel();
        lblOrderID = new beatalbumshop.componment.MyLabel();
        lblPaymentOption = new beatalbumshop.componment.MyLabel();
        lblPaymentOption2 = new beatalbumshop.componment.MyLabel();
        lblMessage = new beatalbumshop.componment.MyLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMessage = new beatalbumshop.componment.MyTextArea();
        lblFullName = new beatalbumshop.componment.MyLabel();
        txtFullName = new beatalbumshop.componment.MyTextField();
        lblPhoneNumber = new beatalbumshop.componment.MyLabel();
        txtPhoneNumber = new beatalbumshop.componment.MyTextField();
        lblAddress = new beatalbumshop.componment.MyLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaAddress = new beatalbumshop.componment.MyTextArea();
        lblSubtotal2 = new beatalbumshop.componment.MyLabel();
        lblSubtotal = new beatalbumshop.componment.MyLabel();
        lblShipping2 = new beatalbumshop.componment.MyLabel();
        lblShipping = new beatalbumshop.componment.MyLabel();
        lblTotal2 = new beatalbumshop.componment.MyLabel();
        lblTotal = new beatalbumshop.componment.MyLabel();
        lblTitleOrderID = new beatalbumshop.componment.MyLabel();
        btnPrint = new beatalbumshop.componment.MyButton();
        cboStatus = new beatalbumshop.componment.MyComboBox();
        btnEdit = new beatalbumshop.componment.MyButton();
        lblDateCreated = new beatalbumshop.componment.MyLabel();
        lblAssignTo = new beatalbumshop.componment.MyLabel();
        myScrollPane1 = new beatalbumshop.componment.MyScrollPane();
        pnlListOrderItem = new javax.swing.JPanel();
        lblStatusIcon = new beatalbumshop.componment.MyLabel();
        cboTitleStaffEmail = new beatalbumshop.componment.MyComboBox();
        cboProductList = new beatalbumshop.componment.MyComboBox();
        btnAddProdcut = new beatalbumshop.componment.MyButton();
        cboQuantity = new beatalbumshop.componment.MyComboBox();
        btnBack = new beatalbumshop.componment.MyButton();

        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlContent.setPreferredSize(new java.awt.Dimension(1037, 600));

        pnlCustomer.setBackground(new java.awt.Color(255, 255, 255));

        lblHeaderCustomer.setText("Customer");

        lblCustomerID2.setForeground(new java.awt.Color(80, 80, 80));
        lblCustomerID2.setText("ID:");
        lblCustomerID2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblCustomerID.setText("customer_id");
        lblCustomerID.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblCustomerEmail.setText("customer_email");
        lblCustomerEmail.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblCustomerEmail2.setForeground(new java.awt.Color(80, 80, 80));
        lblCustomerEmail2.setText("Email:");
        lblCustomerEmail2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlCustomerLayout = new javax.swing.GroupLayout(pnlCustomer);
        pnlCustomer.setLayout(pnlCustomerLayout);
        pnlCustomerLayout.setHorizontalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addComponent(lblHeaderCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addComponent(lblCustomerID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lblCustomerID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addComponent(lblCustomerEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCustomerEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
        );
        pnlCustomerLayout.setVerticalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addComponent(lblHeaderCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlOrderDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlOrderDetails.setPreferredSize(new java.awt.Dimension(380, 384));

        lblHeaderOrderDetails.setText("Order Details");

        lblOrderID2.setForeground(new java.awt.Color(80, 80, 80));
        lblOrderID2.setText("ID:");
        lblOrderID2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblOrderID.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 3));
        lblOrderID.setText("order_id");
        lblOrderID.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblPaymentOption.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 3));
        lblPaymentOption.setText("payment_option");
        lblPaymentOption.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblPaymentOption2.setForeground(new java.awt.Color(80, 80, 80));
        lblPaymentOption2.setText("Payment Option:");
        lblPaymentOption2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblMessage.setForeground(new java.awt.Color(80, 80, 80));
        lblMessage.setText("Message:");
        lblMessage.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        txaMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txaMessage.setColumns(11);
        txaMessage.setLineWrap(true);
        txaMessage.setRows(3);
        txaMessage.setText("customer_message");
        txaMessage.setWrapStyleWord(true);
        txaMessage.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaMessage.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(txaMessage);

        lblFullName.setForeground(new java.awt.Color(80, 80, 80));
        lblFullName.setText("Full Name:");
        lblFullName.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        txtFullName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txtFullName.setText("full_name");
        txtFullName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFullName.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblPhoneNumber.setForeground(new java.awt.Color(80, 80, 80));
        lblPhoneNumber.setText("Phone Number:");
        lblPhoneNumber.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        txtPhoneNumber.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txtPhoneNumber.setText("phone_number");
        txtPhoneNumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPhoneNumber.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblAddress.setForeground(new java.awt.Color(80, 80, 80));
        lblAddress.setText("Address:");
        lblAddress.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        txaAddress.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3)));
        txaAddress.setColumns(11);
        txaAddress.setLineWrap(true);
        txaAddress.setRows(3);
        txaAddress.setText("customer_address");
        txaAddress.setWrapStyleWord(true);
        txaAddress.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaAddress.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(txaAddress);

        lblSubtotal2.setForeground(new java.awt.Color(80, 80, 80));
        lblSubtotal2.setText("Subtotal:");
        lblSubtotal2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblSubtotal.setText("$ 0");
        lblSubtotal.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblShipping2.setForeground(new java.awt.Color(80, 80, 80));
        lblShipping2.setText("Shipping:");
        lblShipping2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblShipping.setText("$ 0");
        lblShipping.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblTotal2.setText("Total:");
        lblTotal2.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblTotal.setText("$ 0");
        lblTotal.setFont(new java.awt.Font("Open Sans", 0, 18)); // NOI18N

        javax.swing.GroupLayout pnlOrderDetailsLayout = new javax.swing.GroupLayout(pnlOrderDetails);
        pnlOrderDetails.setLayout(pnlOrderDetailsLayout);
        pnlOrderDetailsLayout.setHorizontalGroup(
            pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                .addComponent(lblHeaderOrderDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
            .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                        .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrderID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                        .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPaymentOption2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPaymentOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))
                    .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                        .addComponent(lblSubtotal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                        .addComponent(lblShipping2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addComponent(lblShipping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                        .addComponent(lblTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlOrderDetailsLayout.setVerticalGroup(
            pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderDetailsLayout.createSequentialGroup()
                .addComponent(lblHeaderOrderDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentOption2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShipping2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShipping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnlOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        lblTitleOrderID.setText("Order #1");
        lblTitleOrderID.setFont(new java.awt.Font("Open Sans SemiBold", 0, 18)); // NOI18N

        btnPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrint.setText("Print");
        btnPrint.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        cboStatus.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboStatus.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        btnEdit.setBackground(new java.awt.Color(0, 162, 47));
        btnEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 162, 47)));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        lblDateCreated.setText("2023-06-08 12:42:32");
        lblDateCreated.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblAssignTo.setText("asign to");
        lblAssignTo.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        myScrollPane1.setBackground(null);
        myScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        myScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        myScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlListOrderItem.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlListOrderItemLayout = new javax.swing.GroupLayout(pnlListOrderItem);
        pnlListOrderItem.setLayout(pnlListOrderItemLayout);
        pnlListOrderItemLayout.setHorizontalGroup(
            pnlListOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1030, Short.MAX_VALUE)
        );
        pnlListOrderItemLayout.setVerticalGroup(
            pnlListOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 605, Short.MAX_VALUE)
        );

        myScrollPane1.setViewportView(pnlListOrderItem);

        lblStatusIcon.setBackground(new java.awt.Color(80, 80, 80));
        lblStatusIcon.setRadius(20);

        cboTitleStaffEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboTitleStaffEmail.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboTitleStaffEmail.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        cboProductList.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboProductList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboProductList.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        cboProductList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProductListActionPerformed(evt);
            }
        });

        btnAddProdcut.setBackground(new java.awt.Color(0, 162, 47));
        btnAddProdcut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 162, 47)));
        btnAddProdcut.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProdcut.setText("Add");
        btnAddProdcut.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        btnAddProdcut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdcutActionPerformed(evt);
            }
        });

        cboQuantity.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        cboQuantity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cboQuantity.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(pnlOrderDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(cboProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(btnAddProdcut, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblStatusIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTitleOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAssignTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboTitleStaffEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(pnlCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlContentLayout.createSequentialGroup()
                    .addGap(424, 424, 424)
                    .addComponent(myScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTitleStaffEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblAssignTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitleOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(lblDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(pnlCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(pnlOrderDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddProdcut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlContentLayout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addComponent(myScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(103, Short.MAX_VALUE)))
        );

        btnBack.setBackground(null);
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/beatalbumshop/resources/images/icons/back.png"))); // NOI18N
        btnBack.setPreferredSize(new java.awt.Dimension(70, 50));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        MainAdmin.showTab(MainAdmin.prevTab);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Order oldOrder = new Order();
        boolean result = false;
        int updated = 0;
        
        if(btnEdit.getText().equalsIgnoreCase("edit")) {
            //edit
            enableField(true);
            btnEdit.setText("Save");
            txtFullName.requestFocus();
        }
        else {
            //save
            String fullName = txtFullName.getText();
            String address = txaAddress.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String message = txaMessage.getText();
            Double shipping = 0.0;
            long status = OrderStatus.getLongValueByName(cboStatus.getSelectedItem().toString());
            ArrayList<Item> lOrderItem = new ArrayList<>();
            lOrderItem = order.getlOrderItem();
            long staffID = -1;
            if(cboTitleStaffEmail.getSelectedItem() != null) {
                staffID = userDAO.getByEmail(cboTitleStaffEmail.getSelectedItem().toString()).getID();
            }
            
            //validate
            ArrayList<String> errors = new ArrayList<>();

            errors.add(Validator.allowVietnamesePhoneNumber((JTextField)txtPhoneNumber, "PhoneNumber", phoneNumber, false));
            errors.add((!Validator.isNotNull((MyTextArea)txaAddress, address)) ? "Vui lòng nhập Address\n" : "");
            errors.add(Validator.allowVietnameseSpace((JTextField)txtFullName, "Full Name", fullName, false));

            if(cboTitleStaffEmail.getSelectedIndex() == -1 && status != OrderStatus.PENDING && status != OrderStatus.CANCELLED) errors.add("Không thể thay đổi Status khi đơn hàng chưa được phân công cho ai");
//            if(lOrderItem.isEmpty()) errors.add("Đơn hàng sẽ bị Hủy nếu không có sản phẩm nào được chọn\n");

            Collections.reverse(errors);
            String e = "";
            for(String s : errors) e += s;

            //co loi
            if(!e.isEmpty()) {
                MyDialog.display(1, e);
                return;
            }
            else {
                enableField(false);
                btnEdit.setText("Edit");
            }

            oldOrder = new Order(order.getOrderID(), order.getFullName(), order.getAddress(), order.getPhoneNumber(), order.getMessage(), order.getShipping(), order.getStatus(), order.getlOrderItem(), order.getCustomerID(), order.getStaffID(), order.getDateCreated());
            
            //updates
            order.setFullName(fullName);
            order.setAddress(address);
            order.setMessage(message);
            order.setlOrderItem(lOrderItem);
            order.setStatus(status);
            order.setStaffID(staffID); 
            
            // assign to staff by admin - send mail to staff
            if(order.getStaffID() != oldOrder.getStaffID() && staffID != -1) {
                order.setStatus(OrderStatus.PROCESSING);
                
                if(updated == 0) {
                    result = orderDAO.updateByID(order);
                    updated = 1;
                }

                if(result) {
                    //update order thanh cong

                    // send email order has been changed
                    String subject = "ORDER ASSIGNMENT: NEW ORDER #" + order.getOrderID() + " ASSIGNED TO YOU";
                    String recipient = cboTitleStaffEmail.getSelectedItem().toString();

                    String content = "" +
                        "<p>Dear " + userDAO.getByID(staffID).getEmail() + ",</p>\n" +
                        "<p>An order has been assigned to you for processing.</p>\n" +
                        "<p>Order Number: <strong>#" + order.getOrderID() + "</strong></p> <br>\n" +
                        "<p>Customer Email: " + customer.getEmail() + "</p>\n" +
                        "<p>Full Name: " + order.getFullName() + "</p>\n" +
                        "<p>Phone Number: " + order.getPhoneNumber()+ "</p>\n" +
                        "<p>Address: " + order.getAddress()+ "</p> <br>\n" +
                        "<p>Please log in to the system to view the complete order details and proceed with the necessary actions.</p>";

                    boolean sendStatus = SendEmail.sendOrderStatusEmail(recipient, recipient, subject, content); 
                    if(sendStatus) {
                        MainAdmin main = OtherHelper.getMainAdminFrame(this);
                        main.managementOrder.fillToTableNotAssigned();
                        if(!LoggedInUser.isAdmin()) {
                            main.managementOrder.fillToTableAssignToMe();
                        }
                    }
                }
                else {
                    //update that bai
                    MyDialog.display(1, "Có lỗi xảy ra trong quá trình cập nhật đơn hàng.");
                }
            }


            
            // thay doi cac truong thong tin - send email to customer
            if(!order.getFullName().equalsIgnoreCase(oldOrder.getFullName()) ||
                !order.getAddress().equalsIgnoreCase(oldOrder.getAddress()) ||
                !order.getMessage().equalsIgnoreCase(oldOrder.getMessage()) ||
                !order.getlOrderItem().equals(oldOrder.getlOrderItem()) ||
                (order.getStatus() == OrderStatus.PROCESSING && oldOrder.getStatus() == OrderStatus.PENDING)) {
                //thay doi
                if(updated == 0) {
                    result = orderDAO.updateByID(order);
                    updated = 1;
                }

                if(result) {
                    //update order thanh cong

                    // send email order has been changed
                    String subject = "YOUR ORDER #" + order.getOrderID() + " HAS BEEN CHANGED";
                    String recipient = customer.getEmail();

                    String content = "" +
                        "<p>Dear " + fullName + ",</p>\n" +
                        "<p>We're writing to let you know that yout order has been changed.</p>\n" +
                        "<p>Your order Number: <strong>#" + order.getOrderID() + "</strong></p> <br>\n" +
                        "<p>Please login to your account and track your order.</p>";

                    boolean sendStatus = SendEmail.sendOrderStatusEmail(recipient, recipient, subject, content); 
                    if(sendStatus) {
                        MainAdmin main = OtherHelper.getMainAdminFrame(this);
                        main.managementOrder.fillToTableNotAssigned();
                        if(!LoggedInUser.isAdmin()) {
                            main.managementOrder.fillToTableAssignToMe();
                        }
                    }
                }
                else {
                    //update that bai
                    MyDialog.display(1, "Có lỗi xảy ra trong quá trình cập nhật đơn hàng.");
                }
            }
            

            
            //shipped - doi status thanh shipped - send email to customer
            if(order.getStatus() == OrderStatus.SHIPPED && oldOrder.getStatus() == OrderStatus.PROCESSING) {
                if(updated == 0) {
                    result = orderDAO.updateByID(order);
                    updated = 1;
                }

                if(result) {
                    //update order thanh cong

                    // send email order has been changed
                    String subject = "YOUR ORDER #" + order.getOrderID() + " IS ON ITS WAY";
                    String recipient = customer.getEmail();

                    String content = "" +
                        "<p>Dear " + fullName + ",</p>\n" +
                        "<p>GOOD NEWS - YOUR ORDER IS ON ITS WAY TO YOU!!!</p>\n" +
                        "<p>Your order Number: <strong>#" + order.getOrderID() + "</strong></p> <br>\n" +
                        "<p>Please login to your account and track your order.</p>";

                    boolean sendStatus = SendEmail.sendOrderStatusEmail(recipient, recipient, subject, content); 
                    if(sendStatus) {
                        MainAdmin main = OtherHelper.getMainAdminFrame(this);
                        main.managementOrder.fillToTableNotAssigned();
                        if(!LoggedInUser.isAdmin()) {
                            main.managementOrder.fillToTableAssignToMe();
                        }
                    }
                }
                else {
                    //update that bai
                    MyDialog.display(1, "Có lỗi xảy ra trong quá trình cập nhật đơn hàng.");
                }
            }
            

            
            //cancelled - doi status thanh cancelled - send email to customer
            if(order.getStatus() == OrderStatus.CANCELLED && oldOrder.getStatus() != OrderStatus.CANCELLED) {
                // cancelled
                if(updated == 0) {
                    result = orderDAO.updateByID(order);
                    updated = 1;
                }

                if(result) {
                    //update order thanh cong
                    
                    if(oldOrder.getStatus() == OrderStatus.PROCESSING) {
                        //cong lai vao album
                        ArrayList<Album> lA = (ArrayList<Album>) albumDAO.getAll();
                        ArrayList<Item> lI = (ArrayList<Item>) order.getlOrderItem();

                        for(Item i : lI) {
                            for(Album a : lA) {
                                if(i.getAlbumID().equals(a.getAlbumID())) {
                                    a.setInStock(a.getInStock() + i.getQuantity());
                                    boolean re = albumDAO.add(a);
                                    if(!re) {
                                        MyDialog.display(1, "Có lỗi xảy ra khi cập nhật số lượng InStock");
                                    }
                                }
                            }
                        }
                    }

                    // send email order has been changed
                    String subject = "YOUR ORDER #" + order.getOrderID() + " HAS BEEN CANCELED";
                    String recipient = customer.getEmail();

                    String content = "" +
                        "<p>Dear " + fullName + ",</p>\n" +
                        "<p>We regret to inform you that your order with order number <strong>#" + order.getOrderID() + "</strong> has been cancelled. We understand that this may come as a disappointment, and we apologize for any inconvenience caused.</p> <br>\n" +
                        "<p>The cancellation of your order may have occurred due to various reasons, such as stock unavailability, payment issues, or unforeseen circumstances. If you did not request the cancellation or if you have any concerns, please don't hesitate to contact our Client Service. We will be more than happy to assist you and address any queries you may have.</p> <br>\n" +
                        "<p>We value your business and would like to thank you for considering us for your purchase. We apologize for any inconvenience caused and hope to have the opportunity to serve you better in the future.</p>";

                    boolean sendStatus = SendEmail.sendOrderStatusEmail(recipient, recipient, subject, content); 
                    if(sendStatus) {
                        MainAdmin main = OtherHelper.getMainAdminFrame(this);
                        main.managementOrder.fillToTableNotAssigned();
                        if(!LoggedInUser.isAdmin()) {
                            main.managementOrder.fillToTableAssignToMe();
                        }
                    }
                }
                else {
                    //update that bai
                    MyDialog.display(1, "Có lỗi xảy ra trong quá trình cập nhật đơn hàng.");
                }
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddProdcutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdcutActionPerformed
        ArrayList<Item> lItem = new ArrayList<>();
        lItem = order.getlOrderItem();
        
        int selectedIndex = cboProductList.getSelectedIndex();
        Album aInCBO = lProduct.get(selectedIndex);

        //check exist
        boolean added = false;
        boolean error = false; // false = kh add
        int q = Integer.parseInt(cboQuantity.getSelectedItem().toString()); // so tren combobox

        for(Item item : lItem) {
            // da ton tai
            if(item.getAlbumID().equals(aInCBO.getAlbumID())) {
                // them > instock
                if(aInCBO.getInStock() < item.getQuantity() + q) {
                    MyNotification n = new MyNotification((JFrame) SwingUtilities.getWindowAncestor(this), true, "Currently not available");
                    n.setVisible(true);
                    error = true;
                }
                else {
                    item.setQuantity(item.getQuantity() + q);   
                }

                added = true;
                break;
            }
        }

        // chua ton tai
        if(!added) {
            lItem.add(new Item(aInCBO.getAlbumID(), q));
            added = true;
        }

        if(!error && added) {
            // +1 hoac them moi
            //thanh cong
            order.setlOrderItem(lItem);

            //reset
            displayItemInOrder();
        }
    }//GEN-LAST:event_btnAddProdcutActionPerformed

    private void cboProductListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProductListActionPerformed
        if (cboProductList.getSelectedItem() != null) {
            cboProductList.setToolTipText(cboProductList.getSelectedItem().toString());
            int selectedIndex = cboProductList.getSelectedIndex();
            cboQuantity.removeAllItems();
            for(int i = 1; i <= lProduct.get(selectedIndex).getInStock(); i++) {
                cboQuantity.addItem(i);
            }
        }
    }//GEN-LAST:event_cboProductListActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if(btnEdit.getText().equalsIgnoreCase("Edit")) OtherHelper.panelToImage(pnlContent, "OrderID" + order.getOrderID());
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnAddProdcut;
    private beatalbumshop.componment.MyButton btnBack;
    private beatalbumshop.componment.MyButton btnEdit;
    private beatalbumshop.componment.MyButton btnPrint;
    private beatalbumshop.componment.MyComboBox cboProductList;
    private beatalbumshop.componment.MyComboBox cboQuantity;
    private beatalbumshop.componment.MyComboBox cboStatus;
    private beatalbumshop.componment.MyComboBox cboTitleStaffEmail;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private beatalbumshop.componment.MyLabel lblAddress;
    private beatalbumshop.componment.MyLabel lblAssignTo;
    private beatalbumshop.componment.MyLabel lblCustomerEmail;
    private beatalbumshop.componment.MyLabel lblCustomerEmail2;
    private beatalbumshop.componment.MyLabel lblCustomerID;
    private beatalbumshop.componment.MyLabel lblCustomerID2;
    private beatalbumshop.componment.MyLabel lblDateCreated;
    private beatalbumshop.componment.MyLabel lblFullName;
    private beatalbumshop.componment.MyLabel lblHeaderCustomer;
    private beatalbumshop.componment.MyLabel lblHeaderOrderDetails;
    private beatalbumshop.componment.MyLabel lblMessage;
    private beatalbumshop.componment.MyLabel lblOrderID;
    private beatalbumshop.componment.MyLabel lblOrderID2;
    private beatalbumshop.componment.MyLabel lblPaymentOption;
    private beatalbumshop.componment.MyLabel lblPaymentOption2;
    private beatalbumshop.componment.MyLabel lblPhoneNumber;
    private beatalbumshop.componment.MyLabel lblShipping;
    private beatalbumshop.componment.MyLabel lblShipping2;
    private beatalbumshop.componment.MyLabel lblStatusIcon;
    private beatalbumshop.componment.MyLabel lblSubtotal;
    private beatalbumshop.componment.MyLabel lblSubtotal2;
    private beatalbumshop.componment.MyLabel lblTitleOrderID;
    private beatalbumshop.componment.MyLabel lblTotal;
    private beatalbumshop.componment.MyLabel lblTotal2;
    private beatalbumshop.componment.MyScrollPane myScrollPane1;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlListOrderItem;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlOrderDetails;
    private beatalbumshop.componment.MyTextArea txaAddress;
    private beatalbumshop.componment.MyTextArea txaMessage;
    private beatalbumshop.componment.MyTextField txtFullName;
    private beatalbumshop.componment.MyTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
