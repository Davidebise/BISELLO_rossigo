package modello;

public class Noleggio {
    private Utente utente;
    private Veicolo veicolo;
    private int ora_inizio;
    private int ora_fine;

    public Noleggio(Utente utente,Veicolo veicolo,int ora_inizio,int ora_fine){
        this.utente=utente;
        this.veicolo=veicolo;
        this.ora_inizio=ora_inizio;
        this.ora_fine=ora_fine;
    }

}
