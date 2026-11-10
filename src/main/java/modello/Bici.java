package modello;

public class Bici extends Veicolo{
    private int numeroMarce;
    public Bici(int codice,int batteria,statoVeicolo stato,int numeroMarce){
        super(codice,batteria,stato);
        this.numeroMarce=numeroMarce;
    }

    @Override
    public double calcolaCosto(int minuti){
        return 0.15*minuti;
    }
    @Override
    public double consumoBatteriaPerMinuto(){
        return 3;
    }

    public int getNumeroMarce() {
        return numeroMarce;
    }
}
