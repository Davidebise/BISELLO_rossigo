package eccezioni;

public class CreditoInsufficienteException extends RuntimeException {
    public CreditoInsufficienteException(String message) {
        super(message);
    }
}
