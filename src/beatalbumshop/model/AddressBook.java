package beatalbumshop.model;

public class AddressBook {
    private String addressBoookID;
    private String fullName;
    private String address;
    private String phoneNumber;

    public AddressBook() {
    }

    public AddressBook(String addressBoookID, String fullName, String address, String phoneNumber) {
        this.addressBoookID = addressBoookID;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddressBoookID() {
        return addressBoookID;
    }

    public void setAddressBoookID(String addressBoookID) {
        this.addressBoookID = addressBoookID;
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
    
}
