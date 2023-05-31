package beatalbumshop.dao;

import beatalbumshop.model.Customer;

public interface CustomerDAO extends DAO<Customer> {
    public Customer authentication(String email, String password);
    public boolean checkExitByEmail(String email);
}
