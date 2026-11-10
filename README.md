# Progetto RossiGo

Backend in Java (JDK 25) con Maven e SQLite per la gestione di un servizio di sharing di bici e monopattini elettrici.

## Descrizione
Il sistema gestisce la flotta di veicoli (polimorfismo applicato su `Veicolo`), gli utenti con validazione del codice fiscale, i noleggi e la persistenza sicura tramite database SQLite con pattern Singleton e `PreparedStatement`.

## Librerie Esterne Utilizzate
1. **org.xerial:sqlite-jdbc**: Per la connessione e la persistenza dei dati su database SQLite.
2. **com.github.lalyos:jfiglet**: Per stampare il titolo "RossiGo" in stile ASCII art all'avvio dell'applicazione.
3. **it.kamaladafrica:codice-fiscale**: Per validare formalmente il codice fiscale dell'utente in fase di registrazione.

## Come Eseguire il Progetto
1. Clona il repository.
2. Apri il progetto con un IDE compatibile con Maven (es. IntelliJ IDEA).
3. Esegui il build con Maven: `mvn clean install`
4. Esegui la classe `Main` all'interno del package `app`.

## Scenari Simulati
1. **Credito Insufficiente**: Un utente prova a sbloccare un mezzo con meno di 1€ di credito.
2. **Codice Fiscale non valido**: Tentativo di registrazione con un codice formattato male.
3. **Veicolo non disponibile**: Tentativo di sbloccare una bici già in uso.
4. **Batteria scarica**: Tentativo di sblocco di un mezzo con batteria $\le 15\%$.
5. **Terminazione noleggio**: Calcolo dei costi basato sul polimorfismo e aggiornamento della batteria e del credito.
6. **Persistenza**: Verifica del salvataggio dei dati su database SQLite.