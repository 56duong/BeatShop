package beatalbumshop.dao;

import beatalbumshop.model.Customer;

public interface CustomerDAO extends DAO<Customer> {
    public Customer authentication(String email, String password);
    public boolean checkExitByEmail(String email);
    public Customer getByID(long customerID);
    public int updateByEmail(String password ,String email);
    public Customer getByEmail(String email);
}
