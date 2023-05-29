/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package beatalbumshop.dao;

import beatalbumshop.model.User;

/**
 *
 * @author conro
 */
public interface UserDAO extends DAO<User> {

    public User validateUser(String email, String password);

    public User getByEmail(String email);

    public int updatePasswordByEmail(String password, String email);
}
