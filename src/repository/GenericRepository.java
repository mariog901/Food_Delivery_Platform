package repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Optional<T> create(T entity);
    Optional<T> getById(int id);
    List<T> getAll();
    Optional<T> update(T entity);
    boolean delete(int id);
}
