package modello;

public abstract class Veicolo {
    protected String codice;
    protected int batteria;
    protected statoVeicolo stato;

    public Veicolo(String codice,int batteria,statoVeicolo stato){
        this.codice=codice;
        this.batteria=batteria;
        this.stato=stato;
    }

    public abstract double calcolaCosto(int minuti);

    public abstract double consumoBatteriaPerMinuto();

    public String getCodice() {
        return codice;
    }
}

