package beatalbumshop.model;

import beatalbumshop.utils.TimeHelper;

public class U {
    private long ID;
    private String email;
    private String password;
    private String dateCreated = TimeHelper.getCurrentDateTime();

    public U() {
    }

    public U(long ID, String email, String password) {
        this.ID = ID;
        this.email = email;
        this.password = password;
    }
    
    public U(long ID, String email, String password, String dateCreated) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
}
