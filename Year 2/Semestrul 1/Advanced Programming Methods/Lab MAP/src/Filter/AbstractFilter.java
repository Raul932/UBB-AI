package Filter;

public interface AbstractFilter<T> {
    boolean accept(T entity);
}
