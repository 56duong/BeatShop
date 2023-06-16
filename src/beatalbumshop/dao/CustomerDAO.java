package beatalbumshop.dao;

import beatalbumshop.model.Customer;

public interface CustomerDAO extends DAO<Customer> {
    
    /**
     * Authenticates a customer with the given email and password.
     *
     * @param email    the email of the customer
     * @param password the password of the customer
     * @return the Customer object representing the authenticated customer, or null if authentication fails or an error occurs
     */
    public Customer authentication(String email, String password);
    
    /**
     * Checks if a customer with the given email exists in the database.
     *
     * @param email the email to check
     * @return true if the email does not exist, false if the email already exists or an error occurs
     */
    public boolean checkExitByEmail(String email);
    
    /**
     * Retrieves a customer from the database based on their ID.
     *
     * @param customerID the ID of the customer
     * @return the Customer object representing the customer, or null if the customer is not found
     */
    public Customer getByID(long customerID);
    
    /**
     * Updates the password for the customer with the given email.
     *
     * @param password the new password
     * @param email    the email of the customer
     * @return 1 if the update is successful, 0 if an error occurs
     */
    public int updateByEmail(String password ,String email);
    
    /**
     * Retrieves a customer by their email.
     *
     * @param email the email of the customer
     * @return the Customer object representing the customer with the given email, or null if the customer is not found
     */
    public Customer getByEmail(String email);
}
