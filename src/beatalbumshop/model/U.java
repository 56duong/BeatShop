package beatalbumshop.model;

import beatalbumshop.utils.TimeHelper;

/**
 * The U class represents a user with basic properties.
 */
public class U {
    private long ID;
    private String email;
    private String password;
    private String dateCreated = TimeHelper.getCurrentDateTime();

    /**
     * Default constructor for the U class.
     */
    public U() {
    }

    /**
     * Constructor for the U class with ID, email, and password parameters.
     *
     * @param ID       the ID of the user
     * @param email    the email of the user
     * @param password the password of the user
     */
    public U(long ID, String email, String password) {
        this.ID = ID;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Constructor for the U class with ID, email, password, and dateCreated parameters.
     *
     * @param ID          the ID of the user
     * @param email       the email of the user
     * @param password    the password of the user
     * @param dateCreated the date the user was created
     */
    public U(long ID, String email, String password, String dateCreated) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user
     */
    public long getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     *
     * @param ID the ID to set
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the date the user was created.
     *
     * @return the date the user was created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date the user was created.
     *
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
}
