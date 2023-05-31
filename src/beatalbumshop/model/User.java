package beatalbumshop.model;

public class User extends U {
    private long role;

    public User() {
    }

    public User(long role, long ID, String email, String password) {
        super(ID, email, password);
        this.role = role;
    }

    public User(long role, long ID, String email, String password, String dateCreated) {
        super(ID, email, password, dateCreated);
        this.role = role;
    }

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }
    
}
