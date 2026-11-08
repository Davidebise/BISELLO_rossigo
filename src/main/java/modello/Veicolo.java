package modello;

public abstract class Veicolo {
    protected int codice;
    protected int batteria;
    protected statoVeicolo stato;

    public Veicolo(int codice,int batteria,statoVeicolo stato){
        this.codice=codice;
        this.batteria=batteria;
        this.stato=stato;
    }

    public abstract double calcolaCosto(int minuti);

    public abstract double consumoBatteriaPerMinuto();

    public int getCodice() {
        return codice;
    }
    public int getBatteria() {
        return batteria;
    }
    public String getStato() {
        return stato.toString();
    }
}

