package beatalbumshop.dao;

import java.util.List;

/**
 * The DAO interface represents a generic data access object for performing CRUD operations on entities.
 *
 * @param <T> the type of entity handled by the DAO
 */
public interface DAO<T> {
    /**
     * Adds a new entity to the data source.
     *
     * @param t the entity to add
     * @return true if the entity was successfully added, false otherwise
     */
    public boolean add(T t);
    
    /**
     * Updates an existing entity in the data source based on its ID.
     *
     * @param t the updated entity
     * @return true if the entity was successfully updated, false otherwise
     */
    public boolean updateByID(T t);
    
    /**
     * Deletes an entity from the data source based on its ID.
     *
     * @param entityID the ID of the entity to delete
     * @return true if the entity was successfully deleted, false otherwise
     */
    public boolean deleteByID(String entityID);
    
    /**
     * Retrieves an entity from the data source based on its ID.
     *
     * @param entityID the ID of the entity to retrieve
     * @return the retrieved entity, or null if the entity was not found
     */
    public T getByID(String entityID);
    
    /**
     * Retrieves all entities from the data source.
     *
     * @return a list of all entities
     */
    public List<T> getAll();
}

