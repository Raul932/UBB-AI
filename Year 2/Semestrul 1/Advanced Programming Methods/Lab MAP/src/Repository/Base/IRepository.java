package Repository.Base;

import java.util.Optional;

public interface IRepository<ID, T> {
    void add(ID id, T entity);

    Optional<T> delete(ID id);

    void modify(ID id, T entity);

    Optional<T> findById (ID id);

    Iterable<T> getAll();
}
