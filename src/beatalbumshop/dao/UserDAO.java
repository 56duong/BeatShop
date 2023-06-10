package beatalbumshop.dao;

import beatalbumshop.model.User;

public interface UserDAO extends DAO<User> {
    public boolean checkExitByEmail(String email);
    public User authentication(String email, String password);
    
    public User validateUser(String email, String password);
    public User getByEmail(String email);
    public int updatePasswordByEmail(String password, String email);
    
    public User getByID(long userID);
}
