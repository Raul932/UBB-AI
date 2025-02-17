package exceptions;

public class RepositoryExceptions extends Exception {
    public RepositoryExceptions(String message) {
        super(message);
    }
    public RepositoryExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
