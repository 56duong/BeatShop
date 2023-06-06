package beatalbumshop;

import beatalbumshop.componment.MyButton;
import beatalbumshop.componment.MyDialog;
import beatalbumshop.componment.MyLabel;
import beatalbumshop.componment.MyTextArea;
import beatalbumshop.componment.MyTextField;
import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.dao.CustomerDAO;
import beatalbumshop.dao.CustomerDAOImpl;
import beatalbumshop.dao.Firebase;
import beatalbumshop.dao.OrderDAO;
import beatalbumshop.dao.OrderDAOImpl;
import beatalbumshop.model.AddressBook;
import beatalbumshop.model.Album;
import beatalbumshop.model.Item;
import beatalbumshop.model.Customer;
import beatalbumshop.model.LoggedInUser;
import beatalbumshop.model.Order;
import beatalbumshop.utils.ClearComponent;
import beatalbumshop.utils.OtherHelper;
import beatalbumshop.utils.SendEmail;
import beatalbumshop.utils.TimeHelper;
import beatalbumshop.utils.Validator;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Checkout extends javax.swing.JPanel {

    AlbumDAO albumDAO = new AlbumDAOImpl();
    OrderDAO orderDAO = new OrderDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    ArrayList<Item> lBagItem = new ArrayList<>();
    Customer customer;
    double subtotal = 0;
    Component[] lC;
    Component[] lCEvent;
    Main mainFrame = OtherHelper.getMainFrame(this);
    
    public Checkout() {
        initComponents();

        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        lC = new Component[] {txtFullName, txaAddress, txtPhoneNumber};
        
        //focus event
        lCEvent = new Component[] {txtFullName, txaAddress, txtPhoneNumber, txaMessage};
        for(Component c : lCEvent) {
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
    
    
    
    public void enableField(boolean isEnable) {
        for(Component c : lC) {
            if (c instanceof MyTextField) {
                if(isEnable) {
                    ((MyTextField) c).setEnabled(true);
                }
                else {
                    ((MyTextField) c).setEnabled(false);
                }
            }
            else if (c instanceof MyTextArea) {
                if(isEnable) {
                    ((MyTextArea) c).setEnabled(true);
                }
                else {
                    ((MyTextArea) c).setEnabled(false);
                }
            }
        }
    }
    
    
    
    public void displayCheckOutList() {
        subtotal = 0;
        int sumQuantity = 0;
        pnlRight.removeAll();

        customer = (Customer) LoggedInUser.getCurrentUser();
        lblCustomerEmail.setText(customer.getEmail());
        
        ArrayList<AddressBook> lAddressBook = customer.getlAddressBook();
        cboShippingAddress.removeAllItems();
        for(AddressBook ad : lAddressBook) {
            if(ad.getAddressType().equals("0")) cboShippingAddress.addItem("Home");   
            else if(ad.getAddressType().equals("1")) cboShippingAddress.addItem("Work");   
            else if(ad.getAddressType().equals("2")) cboShippingAddress.addItem("Another");   
        }
        cboShippingAddress.addItem("Other...");
        cboShippingAddress.setSelectedIndex(cboShippingAddress.getItemCount() - 1);
        cboShippingAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = cboShippingAddress.getSelectedItem().toString();
                if(selected.equalsIgnoreCase("home")) selected = "0";
                else if(selected.equalsIgnoreCase("work")) selected = "1";
                else if(selected.equalsIgnoreCase("another")) selected = "2";
                if(selected.equalsIgnoreCase("other...")) {
                    enableField(true);
                    ClearComponent.clear(lC);
                    txtFullName.requestFocus();
                }
                else {
                    enableField(false);
                    txaMessage.requestFocus();
                    for(AddressBook ad : lAddressBook) {
                        if(selected.equals(ad.getAddressType())) {
                            txtFullName.setText(ad.getFullName());
                            txaAddress.setText(ad.getAddress());
                            txtPhoneNumber.setText(ad.getPhoneNumber());
                        }
                    }
                }
            }
        });
        
        lBagItem = customer.getlBagItem();
              
        // bag empty
        if(lBagItem.isEmpty()) {
//            pnlRight.setLayout(new BorderLayout());
//            MyLabel lbl = new MyLabel("Currently Empty");
//            lbl.setHorizontalAlignment(JLabel.CENTER);
//            pnlRight.add(lbl);
            mainFrame.getBtnShop().doClick();
            MyDialog.display(1, "Bạn phải chọn sản phẩm cần mua để có thể đặt hàng");
        }
        else {
            for(Item item : lBagItem) {
                
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
            
            mainFrame = OtherHelper.getMainFrame(this);
            btnPlaceOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    btnPlaceOrderActionPerformed(event);
                }
            });
            
            pnlSummary.add(pnlSubtotal);
            pnlSummary.add(pnlShipping);
            pnlSummary.add(pnlTotal);
            pnlSummary.add(btnPlaceOrder);
            
            pnlRight.add(pnlSummary);
            
            pnlRight.revalidate();
            pnlRight.repaint();
        }
    }
    
    
    
    public static long getMaxID(CollectionReference colRef, String column) {
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
                    return document.getLong(column) + 1;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }
    
    
    
    public void btnPlaceOrderActionPerformed(ActionEvent event) {
        Firestore db = Firebase.getFirestore("beat-75a88");
        CollectionReference colRef = db.collection("orders");

        long id = getMaxID(colRef, "orderID");
        String fullName = txtFullName.getText();
        String address = txaAddress.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String message = txaMessage.getText();
        Double shipping = 0.0;
        long status = 0;
        ArrayList<Item> lOrderItem = new ArrayList<>();
        lOrderItem = lBagItem;
        String dateCreated = TimeHelper.getCurrentDateTime();

        //validate
        ArrayList<String> errors = new ArrayList<>();

        errors.add(Validator.allowVietnamesePhoneNumber((JTextField)txtPhoneNumber, "PhoneNumber", phoneNumber, false));
        errors.add((!Validator.isNotNull((JTextArea)txaAddress, address)) ? "Vui lòng nhập Address\n" : "");
        errors.add(Validator.allowVietnameseSpace((JTextField)txtFullName, "Full Name", fullName, false));

        if(lOrderItem.isEmpty()) errors.add("Vui lòng chọn sản phẩm cần mua\n");

        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;

        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }

        //add
        boolean result = orderDAO.add(new Order(id, fullName, address, phoneNumber, message, shipping, status, lOrderItem, customer.getID(), dateCreated));

        if(result) {
            //them order thanh cong
            //redirect to shop
            mainFrame = OtherHelper.getMainFrame(this);
            mainFrame.getBtnShop().doClick();
            
            // send email order confirmation details
            String subject = "ORDER CONFIRMATION DETAILS";
            String recipientName = customer.getEmail();
            String itemList = "";
            double sub = 0.0;

            for(Item item : lBagItem) {
                Album album = albumDAO.getByID(item.getAlbumID());

                if(album.getInStock() > 0) {
                    itemList += "" +
        "            <a class=\"bag-item\">\n" +
        "                <img src=\"https://firebasestorage.googleapis.com/v0/b/beat-75a88.appspot.com/o/albums%252F" + album.getAlbumID() + ".png?alt=media\">\n" +
        "                <div class=\"infor\">\n" +
        "                    <p class=\"bag-item-name\">" + album.getAlbumName() + "</p>\n" +
        "                    <p class=\"bag-item-price\">Price: $" + album.getPrice()+ "</p>\n" +
        "                    <div class=\"select-size-quantity\">\n" +
        "                        <p>Quantity:</p>\n" +
        "                        <p class=\"bag-item-quantity\">" + item.getQuantity()+ "</p>\n" +
        "                    </div><!--select-size-quantity-->\n" +
        "                </div><!--infor-->\n" +
        "            </a><!--bag-item-->";

                    sub += album.getPrice() * item.getQuantity();
                }
            }


            String body = "" +
"                                <html lang=\"en\">\n" +
"                                    <head>\n" +
"                                        <meta charset=\"UTF-8\">\n" +
"                                        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"                                        <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
"                                        <title>PLACE ORDER | BEAT</title>   \n" +
"                                        <!--font-->\n" +
//"                                        <link href=\"https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap\" rel=\"stylesheet\">\n" +
"                                        <style>\n" +
"\n" +
"                                            * { \n" +
"                                                padding: 0;\n" +
"                                                margin: 0;\n" +
"                                                box-sizing: border-box;\n" +
"                                                font-family: Arial, sans-serif;\n" +
"                                            } \n" +
"\n" +
"                                            body {\n" +
"                                                position: relative;\n" +
"                                            }\n" +
"\n" +
"                                            .container{\n" +
"                                                width: 100%;\n" +
"                                                max-width: 500px;\n" +
"                                                position: absolute;\n" +
"                                                top: 50%;\n" +
"                                                left: 50%;\n" +
"                                                transform: translate(-50%);\n" +
"                                                padding: 25px 15px 15px 15px;\n" +
"                                                background: #f2f2f2;\n" +
"                                            }\n" +
"\n" +
"                                            .header{\n" +
"                                                margin-bottom: 30px;\n" +
"                                                text-align: center;\n" +
"                                                text-transform: uppercase;\n" +
"                                            }\n" +
"\n" +
"                                            .header p{\n" +
"                                                margin: 10px;\n" +
"                                                font-size: 12px;\n" +
"                                            }\n" +
"\n" +
"                                            .header .title{\n" +
"                                                margin: 0;\n" +
"                                                padding-top: 5px;\n" +
"                                            }\n" +
"\n" +
"                                            .shipping-address{\n" +
"                                                padding-bottom: 5px;\n" +
"                                            }\n" +
"\n" +
"                                            .shipping-address,\n" +
"                                            .bag-list{\n" +
"                                                margin-bottom: 10px;\n" +
"                                            }\n" +
"\n" +
"                                            .title{ \n" +
"                                                margin-bottom: 5px;\n" +
"                                                padding-top: 45px;\n" +
"                                                font-size: 14px;\n" +
"                                                font-weight: 500;\n" +
"                                                text-transform: uppercase;\n" +
"                                            }\n" +
"\n" +
"                                            .shipping-address .first{\n" +
"                                                padding-top: 0;\n" +
"                                            }\n" +
"\n" +
"                                            .shipping-address p:not(:first-child){\n" +
"                                                font-size: 13px;\n" +
"                                                font-weight: 300;\n" +
"                                                line-height: 20px;\n" +
"                                            }\n" +
"\n" +
"                                            .container>div{\n" +
"                                                overflow-x: hidden;\n" +
"                                            }\n" +
"\n" +
"                                            .bag-list a{\n" +
"                                                text-decoration: none;\n" +
"                                            }\n" +
"\n" +
"                                            .bag-item{\n" +
"                                                display: flex;\n" +
"                                                justify-content: space-between;\n" +
"                                                align-items: center;\n" +
"                                                padding: 15px;\n" +
"                                                border-bottom: 1px solid #e0e0e0;\n" +
"                                            }\n" +
"\n" +
"                                            .bag-item:last-child{\n" +
"                                                border-bottom: none;\n" +
"                                            }\n" +
"\n" +
"                                            .bag-item img{\n" +
"                                                width: 70px;\n" +
"                                            }\n" +
"\n" +
"                                            .infor{\n" +
"                                                width: 100%;\n" +
"                                                margin: 0 10px;\n" +
"                                                color: #777777;\n" +
"                                                font-weight: 400;\n" +
"                                                text-transform: uppercase;\n" +
"                                            }\n" +
"\n" +
"                                            .infor .bag-item-name{\n" +
"                                                margin-bottom: 10px;\n" +
"                                                color: #000000;\n" +
"                                                font-size: 12px;\n" +
"                                            }\n" +
"\n" +
"                                            .infor .bag-item-price{\n" +
"                                                margin-bottom: 3px;\n" +
"                                                font-size: 11px;\n" +
"                                            }\n" +
"\n" +
"                                            .select-size-quantity{\n" +
"                                                display: flex;\n" +
"                                                justify-content: flex-start;\n" +
"                                                align-items: center;\n" +
"                                                margin-bottom: 3px;\n" +
"                                                font-size: 11px;\n" +
"                                            }\n" +
"\n" +
"                                            .select-size-quantity p{\n" +
"                                                margin-right: 5px;\n" +
"                                            }\n" +
"\n" +
"                                            .order-summary{\n" +
"                                                width: fit-content;\n" +
"                                                margin: auto;\n" +
"                                            }\n" +
"\n" +
"                                            .order-summary .title{\n" +
"                                                margin-bottom: 10px;\n" +
"                                                text-align: center;\n" +
"                                            }\n" +
"\n" +
"                                            .table{\n" +
"                                                display: table-row;\n" +
"                                                font-size: 12px;\n" +
"                                                font-weight: 300;\n" +
"                                                text-transform: uppercase;\n" +
"                                            }\n" +
"\n" +
"                                            .row{\n" +
"                                                display: table-row;\n" +
"                                            }\n" +
"\n" +
"                                            .col{\n" +
"                                                display: table-cell;\n" +
"                                                padding: 5px 20px;\n" +
"                                            }\n" +
"\n" +
"                                            .col.right{\n" +
"                                                font-size: 15px;\n" +
"                                                text-align: right;\n" +
"                                            }\n" +
"\n" +
"                                            .last{\n" +
"                                                font-size: 22px !important;\n" +
"                                                font-weight: 400;\n" +
"                                            }\n" +
"\n" +
"                                            .help{\n" +
"                                                margin-top: 50px;\n" +
"                                                font-size: 12px;\n" +
"                                                font-weight: 500;\n" +
"                                                text-align: center;\n" +
"                                                text-transform: uppercase;\n" +
"                                            }\n" +
"\n" +
"                                            .help a{\n" +
"                                                color: #000000;\n" +
"                                            }\n" +
"\n" +
"                                        </style>\n" +
"                                    </head>\n" +
"                                    <body>\n" +
"                                        <div class=\"container\">  \n" +
"                                        <div class=\"header\">\n" +
"                                            <h2>Thank you for your order!</h2>\n" +
"                                            <p>Dear " + fullName + ", Thank you for shopping with BEAT</p>\n" +
"                                            <p>We will call you shortly to confirm your order.</p> \n" +
"                                            <p class=\"title\">Order Number: " + id + "</p>\n" +
"                                            <p class=\"title\">Order Date: " + dateCreated + "</p>\n" +
"                                        </div><!--header-->\n" +
"\n" +
"                                            <div class=\"shipping-address\">\n" +
"                                                <p class=\"title first\">Shipping address</p>\n" +
"                                                <p>" + fullName + "</p>\n" +
"                                                <p>" + phoneNumber + "</p>\n" +
"                                                <p>" + address + "</p>\n" +
"                                                <p>" + message + "</p>\n" +
"                                            </div><!--shipping-address-->\n" +
"\n" +
"                                            <div class=\"bag-list\">\n" +
"                                                <p class=\"title\">YOUR SELECTIONS</p>\n" +
"                                                " + itemList + "\n" +
"                                            </div><!--bag-list-->  \n" +
"\n" +
"                                            <div class=\"order-summary\">\n" +
"                                                <p class=\"title\">Order summary</p>\n" +
"                                                <div class=\"table\">\n" +
"                                                    <div class=\"row\">\n" +
"                                                        <div class=\"col\">Subtotal:</div>\n" +
"                                                        <div class=\"col right\">$ " + sub + "</div>\n" +
"                                                    </div>\n" +
"                                                    <div class=\"row\">\n" +
"                                                        <div class=\"col\">Shipping (COD):</div>\n" +
"                                                        <div class=\"col right\">$ 0</div>\n" +
"                                                    </div>\n" +
"                                                    <div class=\"row\">\n" +
"                                                        <div class=\"col\">Total:</div>\n" +
"                                                        <div class=\"col right last\">$ " + sub + "</div>\n" +
"                                                    </div>\n" +
"                                                </div>\n" +
"                                            </div><!--order-summary--> \n" +
"\n" +
"                                            <p class=\"help\">May we help: <a href=\"mailto:56duong@gmail.com\" target=\"_blank\">56duong@gmail.com</a></p>\n" +
"\n" +
"                                        </div><!--container-->  \n" +
"                                    </body>\n" +
"                                </html>";

            boolean sendStatus = SendEmail.send(recipientName, recipientName, subject, body); 

            if(sendStatus) {
                //reset Shopping Bag
                lBagItem.clear();
                customer.setlBagItem(lBagItem);
                boolean r = customerDAO.updateByID(customer);
                if(r) {
                    LoggedInUser.setCurrentLoggedIn(customer); //update

                    MyDialog.display(4, "Your order was placed successfully!\nWe have sent the order confirmation details to " + recipientName); // + "\nWe will call you shortly to confirm your order.\n"
                }
                else {
                    //reset bag fail
                    MyDialog.display(1, "Có lỗi xảy ra khi xóa các sản phẩm đã mua trong Shopping Bag.");
                }
            }
            else {
                MyDialog.display(1, "Có lỗi xảy ra trong quá trình gửi Email.");
            }
        }
        else {
            //them that bai
            MyDialog.display(1, "Có lỗi xảy ra trong quá trình tạo đơn hàng.");
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
        lblShippingAddress = new beatalbumshop.componment.MyLabel();
        lblLastName = new beatalbumshop.componment.MyLabel();
        txtFullName = new beatalbumshop.componment.MyTextField();
        cboShippingAddress = new beatalbumshop.componment.MyComboBox();
        lblAddress = new beatalbumshop.componment.MyLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAddress = new beatalbumshop.componment.MyTextArea();
        lblPhoneNumber = new beatalbumshop.componment.MyLabel();
        txtPhoneNumber = new beatalbumshop.componment.MyTextField();
        lblMessage = new beatalbumshop.componment.MyLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaMessage = new beatalbumshop.componment.MyTextArea();
        lblMessage1 = new beatalbumshop.componment.MyLabel();
        rdoCOD = new beatalbumshop.componment.MyRadioButton();
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

        lblShippingAddress.setText("Shipping Address");
        lblShippingAddress.setFont(new java.awt.Font("Open Sans", 0, 18)); // NOI18N

        lblLastName.setText("Full Name");

        txtFullName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));

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

        txaMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        txaMessage.setColumns(20);
        txaMessage.setLineWrap(true);
        txaMessage.setRows(2);
        txaMessage.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txaMessage);

        lblMessage1.setText("Payment Option");

        rdoCOD.setSelected(true);
        rdoCOD.setText("COD");
        rdoCOD.setToolTipText("Cash on delivery");

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addComponent(lblMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(rdoCOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)
                            .addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(lblLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(20, 20, 20)
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoCOD, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(361, Short.MAX_VALUE))
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
    private beatalbumshop.componment.MyLabel lblLastName;
    private beatalbumshop.componment.MyLabel lblMessage;
    private beatalbumshop.componment.MyLabel lblMessage1;
    private beatalbumshop.componment.MyLabel lblPhoneNumber;
    private beatalbumshop.componment.MyLabel lblShippingAddress;
    private beatalbumshop.componment.MyScrollPane myScrollPane1;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlListBag;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRight;
    private beatalbumshop.componment.MyRadioButton rdoCOD;
    private beatalbumshop.componment.MyTextArea txaAddress;
    private beatalbumshop.componment.MyTextArea txaMessage;
    private beatalbumshop.componment.MyTextField txtFullName;
    private beatalbumshop.componment.MyTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
