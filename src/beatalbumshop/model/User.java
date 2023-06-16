package beatalbumshop.model;

/**
 * The User class represents a user in the Beat Album Shop application.
 * It extends the U class and includes additional fields for role.
 *
 * @see U
 */
public class User extends U {
    private long role;

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    /**
     * Constructor for the User class with role, ID, email, and password parameters.
     *
     * @param role     the role of the user (0 for Staff, 1 for Admin)
     * @param ID       the ID of the user
     * @param email    the email of the user
     * @param password the password of the user
     */
    public User(long role, long ID, String email, String password) {
        super(ID, email, password);
        this.role = role;
    }

    /**
     * Constructor for the User class with role, ID, email, password, and dateCreated parameters.
     *
     * @param role        the role of the user (0 for Staff, 1 for Admin)
     * @param ID          the ID of the user
     * @param email       the email of the user
     * @param password    the password of the user
     * @param dateCreated the date the user was created
     */
    public User(long role, long ID, String email, String password, String dateCreated) {
        super(ID, email, password, dateCreated);
        this.role = role;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role of the user (0 for Staff, 1 for Admin)
     */
    public long getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set (0 for Staff, 1 for Admin)
     */
    public void setRole(long role) {
        this.role = role;
    }
    
}
