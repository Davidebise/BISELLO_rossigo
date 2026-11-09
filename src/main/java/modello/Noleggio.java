package modello;

import java.time.LocalDateTime;

public class Noleggio {
    private Utente utente;
    private Veicolo veicolo;
    private LocalDateTime ora_inizio;
    private LocalDateTime ora_fine;

    public Noleggio(Utente utente,Veicolo veicolo,LocalDateTime ora_inizio){
        this.utente=utente;
        this.veicolo=veicolo;
        this.ora_inizio=ora_inizio;
        this.ora_fine=null;
    }
    public void setOraFine(LocalDateTime ora_fine){
        this.ora_fine=ora_fine;
    }
    public int getIdUtente() {
        return utente.getId();
    }

    public LocalDateTime getOra_inizio() {
        return ora_inizio;
    }

    public int getCodiceVeicolo() {
        return veicolo.getCodice();
    }

    public LocalDateTime getOra_fine() {
        return ora_fine;
    }
}
