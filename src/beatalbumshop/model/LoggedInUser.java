package beatalbumshop.model;

/**
 * The LoggedInUser class represents the currently logged-in user in the application.
 */
public class LoggedInUser {
    private static Object currentLoggedIn;

    /**
     * Returns the current logged-in user.
     *
     * @return the current logged-in user as an instance of {@link Customer} or {@link User}, or null if no user is logged in
     */
    public static Object getCurrentUser() {
        if(currentLoggedIn instanceof Customer) return (Customer)currentLoggedIn;
        else if(currentLoggedIn instanceof User) return (User)currentLoggedIn;
        
        return null;
    }

    /**
     * Sets the current logged-in user as a customer.
     *
     * @param customer the customer to set as the current logged-in user
     */
    public static void setCurrentLoggedIn(Customer customer) {
        currentLoggedIn = customer;
    }
    
    /**
     * Sets the current logged-in user as a user.
     *
     * @param user the user to set as the current logged-in user
     */
    public static void setCurrentLoggedIn(User user) {
        currentLoggedIn = user;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentLoggedIn != null;
    }

    /**
     * Checks if the currently logged-in user is a customer.
     *
     * @return true if the current user is a customer, false otherwise
     */
    public static boolean isCustomer() {
        return isLoggedIn() && currentLoggedIn instanceof Customer;
    }

    /**
     * Checks if the currently logged-in user is a staff member.
     *
     * @return true if the current user is a staff member, false otherwise
     */
    public static boolean isStaff() {
        return isLoggedIn() && currentLoggedIn instanceof User && ((User)currentLoggedIn).getRole() == 0;
    }

    /**
     * Checks if the currently logged-in user is an admin.
     *
     * @return true if the current user is an admin, false otherwise
     */
    public static boolean isAdmin() {
        return isLoggedIn() && currentLoggedIn instanceof User && ((User)currentLoggedIn).getRole() == 1;
    }

    /**
     * Logs out the current user by setting the current logged-in user to null.
     */
    public static void logOut() {
        currentLoggedIn = null;
    }
    
}
