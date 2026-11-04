package eccezioni;

public class BatteriaScaricaException extends RuntimeException {
    public BatteriaScaricaException(String message) {
        super(message);
    }
}
