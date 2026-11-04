package modello;

public class Bici extends Veicolo{
    private int numeroMarce;
    public Bici(String codice,int batteria,statoVeicolo stato,int numeroMarce){
        super(codice,batteria,stato);
        this.numeroMarce=numeroMarce;
    }

    public double calcolaCosto(int minuti){
        return 0.15*minuti;
    }

    public double consumoBatteriaPerMinuto(){
        return 3;
    }

    public int getNumeroMarce() {
        return numeroMarce;
    }
}
