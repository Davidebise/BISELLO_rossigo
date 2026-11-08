package eccezioni;

public class BatteriaScaricaException extends RossiGoException {
    private int livello;
    public BatteriaScaricaException(String message) {
        super(message);
    }
    public BatteriaScaricaException(String message, int livello) {
        super(message);
        this.livello = livello;
    }
    public int getLivello(){
        return livello;
    }
}
