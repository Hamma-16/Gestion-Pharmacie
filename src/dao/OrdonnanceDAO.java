package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Ordonnance;

public class OrdonnanceDAO {
private static Connection connection;


public OrdonnanceDAO() throws SQLException{
    connection = SingletonConnection.getInstance();
}

public void ajouterOrdonnance(Ordonnance ordonnance) throws SQLException {
    String query = "INSERT INTO ordonnance (id_medicament, id_client, quantite, date) VALUES (?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, ordonnance.getIdMedicament());
    statement.setInt(2, ordonnance.getIdClient());
    statement.setInt(3, ordonnance.getQuantite());
    statement.setDate(4, new java.sql.Date(ordonnance.getDate().getTime()));
    statement.executeUpdate();
}

public void supprimerOrdonnance(int id) throws SQLException {
    String query = "DELETE FROM ordonnance WHERE id_medicament = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, id);
    statement.executeUpdate();
}

public static void modifierOrdonnance(int idMedicament, int idClient, int quantite, Date date, Ordonnance nouvelleOrdonnance) throws SQLException {
    String sql = "UPDATE ordonnance SET id_medicament = ?, id_client = ?, quantite = ?, date = ? WHERE id_medicament = ? AND id_client = ? AND quantite = ? AND date = ?";
    PreparedStatement stmt = null;
    try {
        stmt = connection.prepareStatement(sql);
        stmt.setInt(1, nouvelleOrdonnance.getIdMedicament());
        stmt.setInt(2, nouvelleOrdonnance.getIdClient());
        stmt.setInt(3, nouvelleOrdonnance.getQuantite());
        stmt.setDate(4, new java.sql.Date(nouvelleOrdonnance.getDate().getTime()));
        stmt.setInt(5, idMedicament);
        stmt.setInt(6, idClient);
        stmt.setInt(7, quantite);
        stmt.setDate(8, new java.sql.Date(date.getTime()));
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("La modification de l'ordonnance a échoué, aucune ligne modifiée.");
        }
    } finally {
        if (stmt != null) {
            stmt.close();
        }
    }
}



public static List<Ordonnance> listerOrdonnances() throws SQLException {
    String query = "SELECT * FROM ordonnance";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);
    List<Ordonnance> ordonnances = new ArrayList<>();
    while (resultSet.next()) {
        int idMedicament = resultSet.getInt("id_medicament");
        int idClient = resultSet.getInt("id_client");
        int quantite = resultSet.getInt("quantite");
        Date date = resultSet.getDate("date");
        Ordonnance ordonnance = new Ordonnance(idMedicament, idClient, quantite, date);
        ordonnances.add(ordonnance);
    }
    return ordonnances;
}

public Ordonnance trouverOrdonnanceParId(int id) throws SQLException {
    String query = "SELECT * FROM ordonnance WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, id);
    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
        int idMedicament = resultSet.getInt("id_medicament");
        int idClient = resultSet.getInt("id_client");
        int quantite = resultSet.getInt("quantite");
        Date date = resultSet.getDate("date");
        return new Ordonnance(idMedicament, idClient, quantite, date);
    }
    return null;
}
}