package eccezioni;

public class ErroreDatabaseException extends RuntimeException {
    public ErroreDatabaseException(String message) {
        super(message);
    }
}
