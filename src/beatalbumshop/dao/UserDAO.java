package beatalbumshop.dao;

import beatalbumshop.model.User;

public interface UserDAO extends DAO<User> {
    /**
     * Checks if a user with the given email exists in the database.
     *
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
    public boolean checkExitByEmail(String email);
    
    /**
     * Authenticates a user with the given email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return the User object representing the authenticated user, or null if authentication fails or an error occurs
     */
    public User authentication(String email, String password);
    
    /**
     * Validates a user with the given email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return the User object representing the authenticated user, or null if authentication fails or an error occurs
     */
    public User validateUser(String email, String password);
    
    /**
     * Retrieves a user from the database based on their email.
     *
     * @param email the email of the user
     * @return the User object representing the user with the given email, or null if the user is not found
     */
    public User getByEmail(String email);
    
    /**
     * Updates the password for the user with the given email.
     *
     * @param password the new password
     * @param email    the email of the user
     * @return 1 if the update is successful, 0 if an error occurs
     */
    public int updatePasswordByEmail(String password, String email);
    
    /**
     * Retrieves a user from the database based on their ID.
     *
     * @param userID the ID of the user
     * @return the User object representing the user with the given ID, or null if the user is not found
     */
    public User getByID(long userID);
}
