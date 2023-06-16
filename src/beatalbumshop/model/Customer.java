package beatalbumshop.model;

import java.util.ArrayList;

/**
 * The Customer class represents a customer in the Beat Album Shop.
 * It extends the User class and includes additional fields for address book and bag items.
 *
 * @see U
 */
public class Customer extends U {
    ArrayList<AddressBook> lAddressBook;
    ArrayList<Item> lBagItem;

    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
    }

    /**
     * Constructor for the Customer class with address book, bag items, ID, email, and password parameters.
     *
     * @param lAddressBook  the list of address book entries
     * @param lBagItem      the list of bag items
     * @param ID            the ID of the customer
     * @param email         the email of the customer
     * @param password      the password of the customer
     */
    public Customer(ArrayList<AddressBook> lAddressBook, ArrayList<Item> lBagItem, long ID, String email, String password) {
        super(ID, email, password);
        this.lAddressBook = lAddressBook;
        this.lBagItem = lBagItem;
    }

    /**
     * Constructor for the Customer class with address book, bag items, ID, email, password, and date created parameters.
     *
     * @param lAddressBook  the list of address book entries
     * @param lBagItem      the list of bag items
     * @param ID            the ID of the customer
     * @param email         the email of the customer
     * @param password      the password of the customer
     * @param dateCreated   the date the customer was created
     */
    public Customer(ArrayList<AddressBook> lAddressBook, ArrayList<Item> lBagItem, long ID, String email, String password, String dateCreated) {
        super(ID, email, password, dateCreated);
        this.lAddressBook = lAddressBook;
        this.lBagItem = lBagItem;
    }

    /**
     * Returns the list of address book entries for the customer.
     *
     * @return the list of address book entries
     */
    public ArrayList<AddressBook> getlAddressBook() {
        return lAddressBook;
    }

    /**
     * Sets the list of address book entries for the customer.
     *
     * @param lAddressBook the list of address book entries to set
     */
    public void setlAddressBook(ArrayList<AddressBook> lAddressBook) {
        this.lAddressBook = lAddressBook;
    }

    /**
     * Returns the list of bag items for the customer.
     *
     * @return the list of bag items
     */
    public ArrayList<Item> getlBagItem() {
        return lBagItem;
    }

    /**
     * Sets the list of bag items for the customer.
     *
     * @param lBagItem the list of bag items to set
     */
    public void setlBagItem(ArrayList<Item> lBagItem) {
        this.lBagItem = lBagItem;
    }
    
}
