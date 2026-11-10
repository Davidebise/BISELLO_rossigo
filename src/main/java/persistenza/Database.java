package persistenza;

import eccezioni.ErroreDatabaseException;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
    private Connection connection = null;
    private static Database instance;
    private Database() throws SQLException, ErroreDatabaseException {
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        creaTabelle();
    }

    public static Database getInstance() throws SQLException, ErroreDatabaseException {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void insertUtente(int id,String nome,String email,String codiceFiscale,double credito) throws ErroreDatabaseException {
        //Test connessione
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }
        //Preparazione query
        String query="INSERT INTO Utenti (IdUtente, Nome, Email, CodiceFiscale, Credito) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement= null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, nome);
            statement.setString(3, email);
            statement.setString(4, codiceFiscale);
            statement.setDouble(5, credito);
            statement.execute();
        } catch (SQLException e) {
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }

    public void insertVeicolo(int codice,int batteria,String stato,String tipo) throws ErroreDatabaseException {
        //Test connessione
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }
        //Preparazione query
        String query="INSERT INTO Veicoli (CodVeicolo, LivelloBatteria, Stato, Tipo) VALUES (?, ?, ?, ?)";
        PreparedStatement statement= null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codice);
            statement.setInt(2, batteria);
            statement.setString(3, stato);
            statement.setString(4, tipo);
            statement.execute();
        } catch (SQLException e) {
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }


    public void insertNoleggio(int idUtente, int codiceVeicolo, LocalDateTime ora_inizio, LocalDateTime ora_fine) throws ErroreDatabaseException {
        //Test connessione
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }
        //Preparazione query
        String query="INSERT INTO Noleggi (IdUtente, CodVeicolo, OraInizio, OraFine) VALUES (?, ?, ?, ?)";
        PreparedStatement statement= null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, idUtente);
            statement.setInt(2, codiceVeicolo);
            statement.setObject(3, ora_inizio);
            statement.setObject(4, ora_fine);
            statement.execute();
        } catch (SQLException e) {
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }

    public void AggiornaCreditoUtente(int idUtente,double credito) throws ErroreDatabaseException {
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }

        String query= "UPDATE Utenti SET Credito = ? WHERE IdUtente = ?";
        PreparedStatement statement= null;
        try{
            statement = connection.prepareStatement(query);
            statement.setDouble(1, credito);
            statement.setInt(2, idUtente);
            statement.execute();
        }catch(SQLException e){
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
    }

    public void AggiornaStatoVeicolo(int CodVeicolo,String stato,int livello) throws ErroreDatabaseException {
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }
        String query="UPDATE Veicoli SET Stato=?,LivelloBatteria=? WHERE CodVeicolo = ?";
        PreparedStatement statement= null;
        try{
            statement=connection.prepareStatement(query);
            statement.setString(1,stato);
            statement.setInt(2,livello);
            statement.setInt(3,CodVeicolo);
            statement.execute();
        }catch(SQLException e){
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
    }

    public void AggiornaNoleggio(int CodVeicolo,int IdUtente, LocalDateTime ora_fine) throws ErroreDatabaseException {
        if(!testConnection()){
            throw new ErroreDatabaseException("Impossbile collegarsi");
        }
        String query="UPDATE Noleggi SET OraFine=? WHERE CodVeicolo = ? AND IdUtente = ?";
        PreparedStatement statement= null;
        try{
            statement=connection.prepareStatement(query);
            statement.setObject(1,ora_fine);
            statement.setInt(2,CodVeicolo);
            statement.setInt(3,IdUtente);
            statement.execute();
        }catch(SQLException e){
            throw new ErroreDatabaseException("Insert fallito:"+ e.getMessage());
        }
    }

    private void creaTabelle() throws ErroreDatabaseException {
        String sqlUtenti = "CREATE TABLE IF NOT EXISTS Utenti (" +
                "IdUtente INTEGER PRIMARY KEY, " +
                "Nome VARCHAR(50) not null, " +
                "Email VARCHAR(50), " +
                "CodiceFiscale VARCHAR(20), " +
                "Credito DOUBLE);";

        String sqlVeicoli = "CREATE TABLE IF NOT EXISTS Veicoli (" +
                "CodVeicolo INTEGER PRIMARY KEY, " +
                "LivelloBatteria INTEGER, " +
                "Stato VARCHAR(20) check (Stato IN ('LIBERO', 'IN_USO', 'IN_RICARICA')), " +
                "Tipo VARCHAR(20));";

        String sqlNoleggi = "CREATE TABLE IF NOT EXISTS Noleggi (" +
                "IdUtente INTEGER, " +
                "CodVeicolo INTEGER, " +
                "OraInizio DATETIME, " +
                "OraFine DATETIME," +
                "PRIMARY KEY(IdUtente,CodVeicolo,OraInizio));";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlUtenti);
            stmt.execute(sqlVeicoli);
            stmt.execute(sqlNoleggi);
        } catch (SQLException e) {
            throw new ErroreDatabaseException("Errore nella creazione delle tabelle: " + e.getMessage());
        }
    }

    private boolean testConnection() {
        try {
            return connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}
