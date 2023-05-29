package beatalbumshop.model;

public class User {
    private long userID;
    private String email;
    private String password;
    private String dateCreated;
    private long role;

    public User(long userID, String email, String password, String dateCreated, long role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
        this.role = role;
    }

    public User(String email, String password, String dateCreated, long role) {
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
        this.role = role;
    }

    public User() {
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }
    
}
