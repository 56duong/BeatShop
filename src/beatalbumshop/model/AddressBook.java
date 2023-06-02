package beatalbumshop.model;

public class AddressBook {
    private long addressBoookID;
    private String addressType;
    private String fullName;
    private String address;
    private String phoneNumber;

    public AddressBook() {
    }

    public AddressBook(long addressBoookID, String addressType, String fullName, String address, String phoneNumber) {
        this.addressBoookID = addressBoookID;
        this.addressType = addressType;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getAddressBoookID() {
        return addressBoookID;
    }

    public void setAddressBoookID(long addressBoookID) {
        this.addressBoookID = addressBoookID;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
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
