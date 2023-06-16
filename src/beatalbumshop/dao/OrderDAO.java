package beatalbumshop.dao;

import beatalbumshop.model.Order;

public interface OrderDAO extends DAO<Order> {
    /**
     * Retrieves a order from the database based on its ID.
     *
     * @param orderID the ID of the order
     * @return the Order object representing the order, or null if the order is not found
     */
    public Order getByID(long orderID);
}

