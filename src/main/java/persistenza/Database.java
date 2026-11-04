package persistenza;

import java.sql.*;

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

    public void insertUtente(int id,double prezzo,int quantita){
        //Test connessione
        if(!testConnection()){
            throw new RuntimeException("Impossbile collegarsi");
        }
        //Preparazione query
        String query="INSERT INTO sushi_menu (nome_piatto, prezzo, quantita) VALUES (?, ?, ?)";
        PreparedStatement statement= null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setDouble(2, prezzo);
            statement.setInt(3, quantita);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Insert fallito:"+ e.getMessage());
        }

        //Esecuzione query
    }

    private boolean testConnection() {
        try {
            return connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}
