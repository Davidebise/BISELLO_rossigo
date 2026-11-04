package modello;

import java.util.ArrayList;
import java.util.HashMap;

public class FlottaService {
    static private HashMap<String,Utente> utenti = new HashMap<String,Utente>();
    static private HashMap<String,Veicolo> veicoli = new HashMap<String,Veicolo>();
    static private ArrayList<Noleggio> noleggio = new ArrayList<Noleggio>();

    public static void registraUtente(Utente u){
        utenti.put(u.getCodiceFiscale(),u);
    }
    public static void registraVeicolo(Veicolo v){
        veicoli.put(v.getCodice(),v);
    }

    public static void sbloccaVeicolo(String codice, String idUtente){

    }
    public static void terminaNoleggio(String codice, int minutiUsati){

    }

    public static void mettiInRicarica(String codice){

    }
}
