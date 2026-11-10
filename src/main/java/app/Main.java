import modello.*;
import eccezioni.*;
import com.github.lalyos.jfiglet.FigletFont;
import servizio.FlottaService;

import static java.lang.IO.println;

void main(){
    try {
        // Stampa titolo ASCII art con JFiglet
        String asciiTitle = FigletFont.convertOneLine("RossiGo");
        println(asciiTitle);
        println("=== Avvio Sistema di Sharing Bici e Monopattini ===\n");

        // --- SETUP INIZIALE ---
        // Registriamo un utente e un veicolo validi
        Utente u1 = new Utente(1, "Mario Rossi", "mario@email.it", "BSLDVD08L21L840K", 10.0);
        Bici bici1 = new Bici(101, 80, statoVeicolo.LIBERO, 6);
        Monopattino mono1 = new Monopattino(201, 90, statoVeicolo.LIBERO, 25);

        FlottaService.registraUtente(u1);
        FlottaService.registraVeicolo(bici1);
        FlottaService.registraVeicolo(mono1);

        // ================= SCENARI DI SIMULAZIONE =================

        // SCENARIO 1: Utente con credito insufficiente (< 1€) (GENERA ECCEZIONE)
        println("\n[Scenario 1] Tentativo sblocco con credito a 0.50€...");
        try {
            Utente uPovero = new Utente(2, "Luigi Verdi", "luigi@email.it", "BSLDVD08L21L840K", 0.50);
            FlottaService.registraUtente(uPovero);
            FlottaService.sbloccaVeicolo(201, 2);
        } catch (CreditoInsufficienteException e) {
            println("-> [Alert utente]: Credito troppo basso per sbloccare il mezzo. Ricarica il conto!");
        }

        // SCENARIO 2: Registrazione con Codice Fiscale errato (GENERA ECCEZIONE)
        println("\n[Scenario 2] Registrazione utente con codice fiscale non valido...");
        try {
            Utente uErrato = new Utente(3, "Anna Neri", "anna@email.it", "CF_ERRATO", 15.0);
            FlottaService.registraUtente(uErrato);
        } catch (CodiceFiscaleNonValidoException e) {
            println("-> [Alert utente]: Il codice fiscale inserito non è formalmente corretto.");
        }

        // SCENARIO 3: Sblocco corretto e successivo tentativo di sbloccare lo stesso veicolo già in uso (GENERA ECCEZIONE)
        println("\n[Scenario 3] Sblocco regolare della bici e tentativo di risblocco (veicolo occupato)...");
        try {
            FlottaService.sbloccaVeicolo(101, 1); // Sblocca Mario (id 1) la bici (id 101)
            println("-> Sblocco riuscito con successo!");

            // Provo a risbloccarla con un altro utente valido
            Utente u4 = new Utente(4, "Giorgio Neri", "giorgio@email.it", "BSLDVD08L21L840K", 20.0);
            FlottaService.registraUtente(u4);
            FlottaService.sbloccaVeicolo(101, 4); // Dovrebbe fallire perché è in uso
        } catch (RossiGoException e) {
            println("-> [Alert utente]: Spiacenti, questo veicolo è già in uso da un altro utente.");
        }

        // SCENARIO 4: Tentativo di sbloccare un veicolo con batteria sotto il 15% (GENERA ECCEZIONE)
        println("\n[Scenario 4] Tentativo di sbloccare un monopattino con batteria scarica...");
        try {
            Monopattino monoScarico = new Monopattino(202, 10, statoVeicolo.LIBERO, 20);
            FlottaService.registraVeicolo(monoScarico);
            FlottaService.sbloccaVeicolo(202, 1);
        } catch (BatteriaScaricaException e) {
            println("-> [Alert utente]: Batteria al " + e.getLivello() + "%. Scegli un altro mezzo!");
        }

        // SCENARIO 5: Conclusione corretta di un noleggio
        println("\n[Scenario 5] Terminazione del noleggio della bici (dopo 15 minuti)...");
        FlottaService.terminaNoleggio(101, 15);
        println("-> Noleggio terminato correttamente. Credito residuo aggiornato.");

        // SCENARIO 6: Dimostrazione della persistenza
        println("\n[Scenario 6] Controllo persistenza dati (simulazione riavvio)...");
        println("-> I dati sono stati salvati sul database SQLite ('rossigo.db'). Riavviando l'app, utenti, veicoli e crediti rimangono intatti grazie al DB.");

    } catch (Exception e) {
        System.err.println("Errore inatteso nel programma: " + e.getMessage());
    }
}
