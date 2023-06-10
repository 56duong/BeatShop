package beatalbumshop.model;

import beatalbumshop.utils.TimeHelper;
import java.util.ArrayList;

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

    public Order() {
    }

    // - dateCreated
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

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public ArrayList<Item> getlOrderItem() {
        return lOrderItem;
    }

    public void setlOrderItem(ArrayList<Item> lOrderItem) {
        this.lOrderItem = lOrderItem;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public long getStaffID() {
        return staffID;
    }

    public void setStaffID(long staffID) {
        this.staffID = staffID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(long paymentOption) {
        this.paymentOption = paymentOption;
    }
    
}
