package eccezioni;

public class CodiceFiscaleNonValidoException extends RuntimeException {
    public CodiceFiscaleNonValidoException(String message) {
        super(message);
    }
}
