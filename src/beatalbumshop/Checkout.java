package beatalbumshop;

import beatalbumshop.componment.MyButton;
import beatalbumshop.componment.MyLabel;
import beatalbumshop.componment.MyTextArea;
import beatalbumshop.componment.MyTextField;
import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.dao.CustomerDAO;
import beatalbumshop.dao.CustomerDAOImpl;
import beatalbumshop.model.Album;
import beatalbumshop.model.BagItem;
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import beatalbumshop.utils.OtherHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Checkout extends javax.swing.JPanel {

    AlbumDAO albumDAO = new AlbumDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    ArrayList<BagItem> lBagItem = new ArrayList<>();
    Customer customer;
    double subtotal = 0;
    Component[] lC;
    
    public Checkout() {
        initComponents();
    
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        
        pnlRight.add(new MyLabel("0 item(s)"));
        
        //focus event
        lC = new Component[] {txtFirstName, txtLastName, txaAddress, txtPhoneNumber, txtMessage};
        for(Component c : lC) {
            c.addFocusListener(new FocusListener() {
                public void focusGained(java.awt.event.FocusEvent focusEvent) {
                    if (c instanceof MyTextField) {
                        ((MyTextField) c).setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(3, 3, 3, 3)));
                    }
                    else if (c instanceof MyTextArea) {
                        ((MyTextArea) c).setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(3, 3, 3, 3)));
                    }
                }

                public void focusLost(java.awt.event.FocusEvent focusEvent) {
                    if (c instanceof MyTextField) {
                        ((MyTextField) c).setBorder(new CompoundBorder(new LineBorder(new Color(153, 153, 153)), new EmptyBorder(3, 3, 3, 3)));
                    }
                    else if (c instanceof MyTextArea) {
                        ((MyTextArea) c).setBorder(new CompoundBorder(new LineBorder(new Color(153, 153, 153)), new EmptyBorder(3, 3, 3, 3)));
                    }
                }
            });
        }
        
        displayCheckOutList();
    }
    
    
    
    public void displayCheckOutList() {
        subtotal = 0;
        int sumQuantity = 0;
        pnlRight.removeAll();

        customer = (Customer) LoggedInUser.getCurrentUser();
        lBagItem = customer.getlBagItem();
              
        // bag empty
        if(lBagItem.isEmpty()) {
            pnlRight.setLayout(new BorderLayout());
            MyLabel lbl = new MyLabel("Currently Empty");
            lbl.setHorizontalAlignment(JLabel.CENTER);
            pnlRight.add(lbl);
//            btnCheckout.setVisible(false);
        }
        else {
            for(BagItem item : lBagItem) {
                
                Album album = albumDAO.getByID(item.getAlbumID());
                
                if(album.getInStock() > 0) {
                    SelectionProduct sp = new SelectionProduct(album, item.getQuantity());
                    sp.setMaximumSize(new Dimension(520, 100));
                    sp.getBthRemove().setVisible(false);
                    sp.getCboQuantity().setEnabled(false);
                    
                    pnlRight.add(sp);
                    
                    subtotal += sp.getSubtotal();
                    sumQuantity += item.getQuantity();
                }
            }
            
            // order summary
            JPanel pnlSummary = new JPanel();
            pnlSummary.setBackground(Color.WHITE);
            pnlSummary.setBorder(new EmptyBorder(40, 20, 10, 20));
            
            MyLabel lblTitle = new MyLabel("Order Summary");
            lblTitle.setPreferredSize(new Dimension(510, 30));
            lblTitle.setHorizontalAlignment(JLabel.CENTER);
            lblTitle.setFont(new Font("Open Sans", 1, 18));
            pnlSummary.add(lblTitle);
            
            JPanel pnlSubtotal = new JPanel(new GridLayout(1, 2));
            pnlSubtotal.setBackground(Color.WHITE);
            MyLabel lblSubtotal = new MyLabel("Subtotal - " + sumQuantity + " item(s)");
            lblSubtotal.setPreferredSize(new Dimension(250, 40));
            pnlSubtotal.add(lblSubtotal);
            MyLabel lblSubtotalPrice = new MyLabel("$ " + subtotal);
            lblSubtotalPrice.setPreferredSize(new Dimension(250, 40));
            lblSubtotalPrice.setHorizontalAlignment(JLabel.RIGHT);
            pnlSubtotal.add(lblSubtotalPrice);
            
            JPanel pnlShipping = new JPanel(new GridLayout(1, 2));
            pnlShipping.setBackground(Color.WHITE);
            MyLabel lblShipping = new MyLabel("Shipping");
            lblShipping.setPreferredSize(new Dimension(250, 40));
            pnlShipping.add(lblShipping);
            MyLabel lblShippingPrice = new MyLabel("$ " + "0.0");
            lblShippingPrice.setPreferredSize(new Dimension(250, 40));
            lblShippingPrice.setHorizontalAlignment(JLabel.RIGHT);
            pnlShipping.add(lblShippingPrice);
            
            JPanel pnlTotal = new JPanel(new GridLayout(1, 2));
            pnlTotal.setBackground(Color.WHITE);
            MyLabel lblTotal = new MyLabel("Total");
            lblTotal.setPreferredSize(new Dimension(250, 40));
            pnlTotal.add(lblTotal);
            MyLabel lblTotalPrice = new MyLabel("$ " + subtotal);
            lblTotalPrice.setFont(new Font("Open Sans", 1, 18));
            lblTotalPrice.setPreferredSize(new Dimension(250, 40));
            lblTotalPrice.setHorizontalAlignment(JLabel.RIGHT);
            pnlTotal.add(lblTotalPrice);
            
            MyButton btnPlaceOrder = new MyButton();
            btnPlaceOrder.setText("Place Order");
            btnPlaceOrder.setPreferredSize(new Dimension(510, 35));
            btnPlaceOrder.setBackground(Color.BLACK);
            btnPlaceOrder.setForeground(Color.WHITE);
            
            pnlSummary.add(pnlSubtotal);
            pnlSummary.add(pnlShipping);
            pnlSummary.add(pnlTotal);
            pnlSummary.add(btnPlaceOrder);
            
            pnlRight.add(pnlSummary);
            
            pnlRight.revalidate();
            pnlRight.repaint();
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

        pnlMain = new javax.swing.JPanel();
        btnClose = new beatalbumshop.componment.MyButton();
        myScrollPane1 = new beatalbumshop.componment.MyScrollPane();
        pnlListBag = new javax.swing.JPanel();
        pnlLeft = new javax.swing.JPanel();
        lblCheckingOutAs = new beatalbumshop.componment.MyLabel();
        lblCustomerEmail = new beatalbumshop.componment.MyLabel();
        lblFirstName = new beatalbumshop.componment.MyLabel();
        txtFirstName = new beatalbumshop.componment.MyTextField();
        lblShippingAddress = new beatalbumshop.componment.MyLabel();
        myLabel3 = new beatalbumshop.componment.MyLabel();
        txtLastName = new beatalbumshop.componment.MyTextField();
        cboShippingAddress = new beatalbumshop.componment.MyComboBox();
        lblAddress = new beatalbumshop.componment.MyLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAddress = new beatalbumshop.componment.MyTextArea();
        lblPhoneNumber = new beatalbumshop.componment.MyLabel();
        txtPhoneNumber = new beatalbumshop.componment.MyTextField();
        lblMessage = new beatalbumshop.componment.MyLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessage = new beatalbumshop.componment.MyTextArea();
        pnlRight = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(1030, 658));
        setMinimumSize(new java.awt.Dimension(1030, 658));
        setPreferredSize(new java.awt.Dimension(1030, 658));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        btnClose.setBackground(null);
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/beatalbumshop/resources/images/icons/back.png"))); // NOI18N
        btnClose.setText(" Back to Shopping Bag");
        btnClose.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N
        btnClose.setPreferredSize(new java.awt.Dimension(70, 50));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        myScrollPane1.setBackground(null);
        myScrollPane1.setBorder(null);
        myScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlListBag.setBackground(new java.awt.Color(255, 255, 255));
        pnlListBag.setPreferredSize(new java.awt.Dimension(1028, 800));
        pnlListBag.setLayout(new java.awt.BorderLayout());

        pnlLeft.setBackground(new java.awt.Color(255, 255, 255));
        pnlLeft.setPreferredSize(new java.awt.Dimension(478, 609));

        lblCheckingOutAs.setText("You are checking out as:");
        lblCheckingOutAs.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblCustomerEmail.setText("Customer123@gmail.com");
        lblCustomerEmail.setFont(new java.awt.Font("Open Sans", 0, 14)); // NOI18N

        lblFirstName.setText("First Name");

        txtFirstName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        lblShippingAddress.setText("Shipping Address");
        lblShippingAddress.setFont(new java.awt.Font("Open Sans", 0, 18)); // NOI18N

        myLabel3.setText("Last Name");

        txtLastName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        cboShippingAddress.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        lblAddress.setText("Address");

        jScrollPane1.setBackground(null);
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        txaAddress.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txaAddress.setColumns(20);
        txaAddress.setLineWrap(true);
        txaAddress.setRows(2);
        txaAddress.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txaAddress);

        lblPhoneNumber.setText("P. Number");

        txtPhoneNumber.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        lblMessage.setText("Message");

        jScrollPane2.setBackground(null);
        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(false);

        txtMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txtMessage.setColumns(20);
        txtMessage.setLineWrap(true);
        txtMessage.setRows(2);
        txtMessage.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtMessage);

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(myLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)))
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addComponent(lblShippingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboShippingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCustomerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheckingOutAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblCheckingOutAs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lblCustomerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShippingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboShippingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(354, Short.MAX_VALUE))
        );

        pnlListBag.add(pnlLeft, java.awt.BorderLayout.LINE_START);

        pnlRight.setBackground(new java.awt.Color(255, 255, 255));
        pnlRight.setPreferredSize(new java.awt.Dimension(540, 800));

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        pnlListBag.add(pnlRight, java.awt.BorderLayout.CENTER);

        myScrollPane1.setViewportView(pnlListBag);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(819, Short.MAX_VALUE))
            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(myScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(616, Short.MAX_VALUE))
            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                    .addGap(0, 47, Short.MAX_VALUE)
                    .addComponent(myScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        Main mainFrame = OtherHelper.getMainFrame(this);
        mainFrame.getBtnShoppingBag().doClick();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private beatalbumshop.componment.MyButton btnClose;
    private beatalbumshop.componment.MyComboBox cboShippingAddress;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private beatalbumshop.componment.MyLabel lblAddress;
    private beatalbumshop.componment.MyLabel lblCheckingOutAs;
    private beatalbumshop.componment.MyLabel lblCustomerEmail;
    private beatalbumshop.componment.MyLabel lblFirstName;
    private beatalbumshop.componment.MyLabel lblMessage;
    private beatalbumshop.componment.MyLabel lblPhoneNumber;
    private beatalbumshop.componment.MyLabel lblShippingAddress;
    private beatalbumshop.componment.MyLabel myLabel3;
    private beatalbumshop.componment.MyScrollPane myScrollPane1;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlListBag;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRight;
    private beatalbumshop.componment.MyTextArea txaAddress;
    private beatalbumshop.componment.MyTextField txtFirstName;
    private beatalbumshop.componment.MyTextField txtLastName;
    private beatalbumshop.componment.MyTextArea txtMessage;
    private beatalbumshop.componment.MyTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
