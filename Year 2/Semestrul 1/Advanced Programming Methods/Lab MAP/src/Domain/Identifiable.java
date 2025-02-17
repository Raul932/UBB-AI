package Domain;

public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
