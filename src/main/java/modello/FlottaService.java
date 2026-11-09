package modello;

import eccezioni.*;
import it.kamaladafrica.codicefiscale.CodiceFiscale;
import persistenza.Database;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class FlottaService {
    static private HashMap<Integer,Utente> utenti = new HashMap<Integer,Utente>();
    static private HashMap<Integer,Veicolo> veicoli = new HashMap<Integer,Veicolo>();
    static private ArrayList<Noleggio> noleggi = new ArrayList<Noleggio>();
    static private Database database = null;

    public static void registraUtente(Utente u) throws RossiGoException, SQLException {
        database=Database.getInstance();
        if(utenti.containsKey(u.getId())){
            throw new RossiGoException("Id Utente già utilizzato!");
        }
        if(!CodiceFiscale.isFormatValid(u.getCodiceFiscale())){
            throw new CodiceFiscaleNonValidoException("Codice fiscale non valido!");
        }
        utenti.put(u.getId(),u);
        database.insertUtente(u.getId(),u.getNome(),u.getEmail(),u.getCodiceFiscale(),u.getCredito());
    }
    public static void registraVeicolo(Veicolo v) throws SQLException, RossiGoException {
        if(veicoli.containsKey(v.getCodice())){
            throw new RossiGoException("Id Veicolo già utilizzato!");
        }
        database=Database.getInstance();
        veicoli.put(v.getCodice(),v);
        if(v instanceof Bici){
            database.insertVeicolo(v.getCodice(),v.getBatteria(),v.getStato(),"bici");
        }else{
            database.insertVeicolo(v.getCodice(),v.getBatteria(),v.getStato(),"monopattino");
        }

    }

    public static void sbloccaVeicolo(int codice, int idUtente) throws RossiGoException, SQLException {
        if(!utenti.containsKey(idUtente)){
            throw new UtenteNonTrovatoException("Id Utente già utilizzato!");
        }
        if(!veicoli.containsKey(codice)){
            throw new RossiGoException("Veicolo non trovato!");
        }
        if(Objects.equals(veicoli.get(codice).getStato(), "IN_USO")){
            throw new VeicoloNonDisponibileException("Veicolo già in uso!");
        }
        if(veicoli.get(codice).getBatteria()<=15){
            throw new BatteriaScaricaException("Batteria Scarica!",veicoli.get(codice).getBatteria());
        }
        if(utenti.get(idUtente).getCredito()<1){
            throw new CreditoInsufficienteException("Credito insufficiente!");
        }
        database=Database.getInstance();
        noleggi.add(new Noleggio(utenti.get(idUtente),veicoli.get(codice),LocalDateTime.now()));
        Noleggio noleggio = noleggi.getLast();
        database.insertNoleggio(noleggio.getIdUtente(), noleggio.getCodiceVeicolo(),noleggio.getOra_inizio(),null);
    }
    public static void terminaNoleggio(int codice, int minutiUsati) throws RossiGoException, SQLException {
        Noleggio found=null;
        for(Noleggio noleggio : noleggi){
            if(noleggio.getCodiceVeicolo()==codice){
                noleggio.setOraFine(noleggio.getOra_inizio().plusMinutes(minutiUsati));
                found = noleggio;
                break;
            }
        }
        if(found == null){
            throw new RossiGoException("Veicolo non trovato!");
        }
        Veicolo v = veicoli.get(codice);
        v.setStato(statoVeicolo.LIBERO);
        v.setBatteria((int)(v.getBatteria()-v.consumoBatteriaPerMinuto()*minutiUsati));
        database=Database.getInstance();
        database.AggiornaStatoVeicolo(codice,v.getStato(),v.getBatteria());
        database.AggiornaNoleggio(found.getIdUtente(),codice,found.getOra_fine() );
    }

    public static void mettiInRicarica(String codice){

    }
}
