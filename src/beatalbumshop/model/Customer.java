package beatalbumshop.model;

import java.util.ArrayList;

public class Customer extends U {
    ArrayList<AddressBook> lAddressBook;

    public Customer() {
    }

    public Customer(ArrayList<AddressBook> lAddressBook, long ID, String email, String password) {
        super(ID, email, password);
        this.lAddressBook = lAddressBook;
    }

    public Customer(ArrayList<AddressBook> lAddressBook, long ID, String email, String password, String dateCreated) {
        super(ID, email, password, dateCreated);
        this.lAddressBook = lAddressBook;
    }

    public ArrayList<AddressBook> getlAddressBook() {
        return lAddressBook;
    }

    public void setlAddressBook(ArrayList<AddressBook> lAddressBook) {
        this.lAddressBook = lAddressBook;
    }
    
}
