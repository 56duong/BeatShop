package beatalbumshop.model;

public class LoggedInUser {
    private static Object currentLoggedIn;

    public static Object getCurrentUser() {
        if(currentLoggedIn instanceof Customer) return (Customer)currentLoggedIn;
        else if(currentLoggedIn instanceof User) return (User)currentLoggedIn;
        
        return null;
    }

    public static void setCurrentLoggedIn(Customer customer) {
        currentLoggedIn = customer;
    }
    
    public static void setCurrentLoggedIn(User user) {
        currentLoggedIn = user;
    }

    public static boolean isLoggedIn() {
        return currentLoggedIn != null;
    }

    public static boolean isCustomer() {
        return isLoggedIn() && currentLoggedIn instanceof Customer;
    }

    public static boolean isStaff() {
        return isLoggedIn() && currentLoggedIn instanceof User && ((User)currentLoggedIn).getRole() == 0;
    }

    public static boolean isAdmin() {
        return isLoggedIn() && currentLoggedIn instanceof User && ((User)currentLoggedIn).getRole() == 1;
    }

    public static void logOut() {
        currentLoggedIn = null;
    }
    
}
