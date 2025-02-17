package Validation;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidatorException;
}
