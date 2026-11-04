package modello;

public class Monopattino extends Veicolo{
    int velocitaMassima;
    public Monopattino(String codice,int batteria,statoVeicolo stato,int velocitaMassima){
        super(codice,batteria,stato);
        this.velocitaMassima=velocitaMassima;
    }

    @Override
    public double calcolaCosto(int minuti){
        return 0.25*minuti;
    }
    @Override
    public double consumoBatteriaPerMinuto(){
        return 5;
    }

}
