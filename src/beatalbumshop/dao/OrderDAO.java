package beatalbumshop.dao;

import beatalbumshop.model.Order;

public interface OrderDAO extends DAO<Order> {
    public Order getByID(long orderID);
}

