package persistenza;

import eccezioni.ErroreDatabaseException;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
    private Connection connection = null;
    private static Database instance;
    private Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
    }

    public static Database getInstance() throws SQLException {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void insertUtente(int id,String nome,String email,String codiceFiscale,double credito){
        //Test connessione
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
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
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }

    public void insertVeicolo(int codice,int batteria,String stato,String tipo){
        //Test connessione
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
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
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }


    public void insertNoleggio(int idUtente, int codiceVeicolo, LocalDateTime ora_inizio, LocalDateTime ora_fine){
        //Test connessione
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
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
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
        }
        //Esecuzione query
    }

    public void AggiornaCreditoUtente(int idUtente,double credito){
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
        }

        String query= "UPDATE Utenti SET Credito = ? WHERE IdUtente = ?";
        PreparedStatement statement= null;
        try{
            statement = connection.prepareStatement(query);
            statement.setDouble(1, credito);
            statement.setInt(2, idUtente);
            statement.execute();
        }catch(SQLException e){
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
        }
    }

    public void AggiornaStatoVeicolo(String stato,int livello){
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
        }
        String query="UPDATE Veicoli SET Stato=?,LivelloBatteria=? WHERE CodVeicolo = ?";
        PreparedStatement statement= null;
        try{
            statement=connection.prepareStatement(query);
            statement.setString(1,stato);
            statement.setInt(2,livello);
        }catch(SQLException e){
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
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
