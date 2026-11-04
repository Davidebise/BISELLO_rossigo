package modello;

public class Monopattino extends Veicolo{
    int velocitaMassima;
    public Monopattino(String codice,int batteria,statoVeicolo stato,int velocitaMassima){
        super(codice,batteria,stato);
        this.velocitaMassima=velocitaMassima;
    }

    public double calcolaCosto(int minuti){
        return 0.25*minuti;
    }

    public double consumoBatteriaPerMinuto(){
        return 5;
    }

}
