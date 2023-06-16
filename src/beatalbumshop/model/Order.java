package beatalbumshop.model;

import beatalbumshop.dao.AlbumDAO;
import beatalbumshop.dao.AlbumDAOImpl;
import beatalbumshop.utils.TimeHelper;
import java.util.ArrayList;

/**
 * The Order class represents an order made by a customer.
 */
public class Order {
    private long orderID;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String message;
    private Double shipping;
    private long paymentOption = 0;
    private long status;
    private ArrayList<Item> lOrderItem;
    private long customerID;
    private long staffID = -1;
    private String dateCreated = TimeHelper.getCurrentDateTime();

    /**
     * Default constructor for the Order class.
     */
    public Order() {
    }

    // - dateCreated
    /**
     * Constructor for the Order class with specified parameters.
     *
     * @param orderID      the ID of the order
     * @param fullName     the full name of the customer
     * @param address      the address of the customer
     * @param phoneNumber  the phone number of the customer
     * @param message      the message or note for the order
     * @param shipping     the shipping cost for the order
     * @param status       the status of the order
     * @param lOrderItem   the list of order items
     * @param customerID   the ID of the customer associated with the order
     * @param staffID      the ID of the staff member associated with the order
     */
    public Order(long orderID, String fullName, String address, String phoneNumber, String message, Double shipping, long status, ArrayList<Item> lOrderItem, long customerID, long staffID) {
        this.orderID = orderID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.shipping = shipping;
        this.status = status;
        this.lOrderItem = lOrderItem;
        this.customerID = customerID;
        this.staffID = staffID;
    }

    /**
     * Constructor for the Order class with specified parameters including dateCreated.
     *
     * @param orderID      the ID of the order
     * @param fullName     the full name of the customer
     * @param address      the address of the customer
     * @param phoneNumber  the phone number of the customer
     * @param message      the message or note for the order
     * @param shipping     the shipping cost for the order
     * @param status       the status of the order
     * @param lOrderItem   the list of order items
     * @param customerID   the ID of the customer associated with the order
     * @param staffID      the ID of the staff member associated with the order
     * @param dateCreated  the date and time when the order was created
     */
    public Order(long orderID, String fullName, String address, String phoneNumber, String message, Double shipping, long status, ArrayList<Item> lOrderItem, long customerID, long staffID, String dateCreated) {
        this.orderID = orderID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.shipping = shipping;
        this.status = status;
        this.lOrderItem = lOrderItem;
        this.customerID = customerID;
        this.staffID = staffID;
        this.dateCreated = dateCreated;
    }

    // - staffID, dateCreated
    /**
     * Constructor for the Order class with specified parameters excluding staffID and dateCreated.
     *
     * @param orderID      the ID of the order
     * @param fullName     the full name of the customer
     * @param address      the address of the customer
     * @param phoneNumber  the phone number of the customer
     * @param message      the message or note for the order
     * @param shipping     the shipping cost for the order
     * @param status       the status of the order
     * @param lOrderItem   the list of order items
     * @param customerID   the ID of the customer associated with the order
     */
    public Order(long orderID, String fullName, String address, String phoneNumber, String message, Double shipping, long status, ArrayList<Item> lOrderItem, long customerID) {
        this.orderID = orderID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.shipping = shipping;
        this.status = status;
        this.lOrderItem = lOrderItem;
        this.customerID = customerID;
    }
    
    // - staffID
    /**
     * Constructor for the Order class with specified parameters excluding staffID.
     *
     * @param orderID      the ID of the order
     * @param fullName     the full name of the customer
     * @param address      the address of the customer
     * @param phoneNumber  the phone number of the customer
     * @param message      the message or note for the order
     * @param shipping     the shipping cost for the order
     * @param status       the status of the order
     * @param lOrderItem   the list of order items
     * @param customerID   the ID of the customer associated with the order
     * @param dateCreated  the date and time when the order was created
     */
    public Order(long orderID, String fullName, String address, String phoneNumber, String message, Double shipping, long status, ArrayList<Item> lOrderItem, long customerID, String dateCreated) {
        this.orderID = orderID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.shipping = shipping;
        this.status = status;
        this.lOrderItem = lOrderItem;
        this.customerID = customerID;
        this.dateCreated = dateCreated;
    }

    /**
     * Retrieves the ID of the order.
     *
     * @return the order ID
     */
    public long getOrderID() {
        return orderID;
    }

    /**
     * Sets the ID of the order.
     *
     * @param orderID the order ID to set
     */
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    /**
     * Retrieves the full name of the customer associated with the order.
     *
     * @return the customer's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the customer associated with the order.
     *
     * @param fullName the customer's full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Retrieves the address of the customer associated with the order.
     *
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer associated with the order.
     *
     * @param address the customer's address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the phone number of the customer associated with the order.
     *
     * @return the customer's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer associated with the order.
     *
     * @param phoneNumber the customer's phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the message or note for the order.
     *
     * @return the order message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message or note for the order.
     *
     * @param message the order message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the shipping cost for the order.
     *
     * @return the shipping cost
     */
    public Double getShipping() {
        return shipping;
    }

    /**
     * Sets the shipping cost for the order.
     *
     * @param shipping the shipping cost to set
     */
    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    /**
     * Retrieves the status of the order.
     *
     * @return the order status
     */
    public long getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status the order status to set
     */
    public void setStatus(long status) {
        this.status = status;
    }

    /**
     * Retrieves the list of order items.
     *
     * @return the list of order items
     */
    public ArrayList<Item> getlOrderItem() {
        return lOrderItem;
    }

    /**
     * Sets the list of order items.
     *
     * @param lOrderItem the list of order items to set
     */
    public void setlOrderItem(ArrayList<Item> lOrderItem) {
        this.lOrderItem = lOrderItem;
    }

    /**
     * Retrieves the ID of the customer associated with the order.
     *
     * @return the customer ID
     */
    public long getCustomerID() {
        return customerID;
    }

    /**
     * Sets the ID of the customer associated with the order.
     *
     * @param customerID the customer ID to set
     */
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    /**
     * Retrieves the ID of the staff member associated with the order.
     *
     * @return the staff ID
     */
    public long getStaffID() {
        return staffID;
    }

    /**
     * Sets the ID of the staff member associated with the order.
     *
     * @param staffID the staff ID to set
     */
    public void setStaffID(long staffID) {
        this.staffID = staffID;
    }

    /**
     * Retrieves the date and time when the order was created.
     *
     * @return the date and time when the order was created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date and time when the order was created.
     *
     * @param dateCreated the date and time to set
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Retrieves the payment option chosen for the order.
     *
     * @return the payment option
     */
    public long getPaymentOption() {
        return paymentOption;
    }

    /**
     * Sets the payment option chosen for the order.
     *
     * @param paymentOption the payment option to set
     */
    public void setPaymentOption(long paymentOption) {
        this.paymentOption = paymentOption;
    }
    
    
    
    /**
     * Calculates and retrieves the total cost of the order.
     *
     * @return the total cost of the order
     */
    public Double getTotal() {
        Double total = 0.0;
        AlbumDAO albumDAO = new AlbumDAOImpl();
        
        for(Item item : lOrderItem) {
            Album album = albumDAO.getByID(item.getAlbumID());
            if(album.getInStock() > 0) {
                total += item.getQuantity() * album.getPrice();
            }
        }
        
        return total;
    }
    
    
    
    /**
     * Retrieves the date when the order was created.
     *
     * @return the date when the order was created
     */
    public String getDate() {
        return getDateCreated().substring(0, 10);
    }
}
