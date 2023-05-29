package beatalbumshop.dao;

import java.util.List;

public interface DAO<T> {
    public boolean add(T t);
    public boolean updateByID(T t);
    public boolean deleteByID(String entityID);
    public T getByID(String entityID);
    public List<T> getAll();
}

