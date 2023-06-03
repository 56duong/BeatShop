package beatalbumshop.model;

import java.util.ArrayList;

public class Customer extends U {
    ArrayList<AddressBook> lAddressBook;
    ArrayList<BagItem> lBagItem;

    public Customer() {
    }

    public Customer(ArrayList<AddressBook> lAddressBook, ArrayList<BagItem> lBagItem, long ID, String email, String password) {
        super(ID, email, password);
        this.lAddressBook = lAddressBook;
        this.lBagItem = lBagItem;
    }

    public Customer(ArrayList<AddressBook> lAddressBook, ArrayList<BagItem> lBagItem, long ID, String email, String password, String dateCreated) {
        super(ID, email, password, dateCreated);
        this.lAddressBook = lAddressBook;
        this.lBagItem = lBagItem;
    }

    public ArrayList<AddressBook> getlAddressBook() {
        return lAddressBook;
    }

    public void setlAddressBook(ArrayList<AddressBook> lAddressBook) {
        this.lAddressBook = lAddressBook;
    }

    public ArrayList<BagItem> getlBagItem() {
        return lBagItem;
    }

    public void setlBagItem(ArrayList<BagItem> lBagItem) {
        this.lBagItem = lBagItem;
    }
    
}
