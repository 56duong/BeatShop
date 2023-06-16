package beatalbumshop.model;

/**
 * The AddressBook class represents an address book entry in the Beat Album Shop.
 */

public class AddressBook {
    private long addressBoookID;
    private String addressType;
    private String fullName;
    private String address;
    private String phoneNumber;

    /**
     * Default constructor for the AddressBook class.
     */
    public AddressBook() {
    }

    /**
     * Constructor for the AddressBook class with address book ID, address type, full name, address, and phone number parameters.
     *
     * @param addressBoookID the ID of the address book entry
     * @param addressType    the type of address (e.g., home, work)
     * @param fullName       the full name associated with the address
     * @param address        the address
     * @param phoneNumber    the phone number
     */
    public AddressBook(long addressBoookID, String addressType, String fullName, String address, String phoneNumber) {
        this.addressBoookID = addressBoookID;
        this.addressType = addressType;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the ID of the address book entry.
     *
     * @return the ID of the address book entry
     */
    public long getAddressBoookID() {
        return addressBoookID;
    }

    /**
     * Sets the ID of the address book entry.
     *
     * @param addressBoookID the ID to set
     */
    public void setAddressBoookID(long addressBoookID) {
        this.addressBoookID = addressBoookID;
    }

    /**
     * Returns the type of address.
     *
     * @return the type of address
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the type of address.
     *
     * @param addressType the address type to set
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    /**
     * Returns the full name associated with the address.
     *
     * @return the full name associated with the address
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name associated with the address.
     *
     * @param fullName the full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
