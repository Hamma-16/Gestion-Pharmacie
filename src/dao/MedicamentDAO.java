package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Medicament;

public class MedicamentDAO {
    private Connection connection;

    public MedicamentDAO() throws SQLException {
        connection = SingletonConnection.getInstance();
    }

    public void ajouterMedicament(Medicament medicament) throws SQLException {
        String query = "INSERT INTO medicament (nom, description, prix, quantite) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, medicament.getNom());
        statement.setString(2, medicament.getDescription());
        statement.setDouble(3, medicament.getPrix());
        statement.setInt(4, medicament.getQuantite());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            medicament.setId(id);
        }
    }

    public void supprimerMedicament(int id) throws SQLException {
        String query = "DELETE FROM medicament WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void modifierMedicament(Medicament medicament) throws SQLException {
        String query = "UPDATE medicament SET nom = ?, description = ?, prix = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, medicament.getNom());
        statement.setString(2, medicament.getDescription());
        statement.setDouble(3, medicament.getPrix());
        statement.setInt(4, medicament.getId());
        statement.executeUpdate();
    }

    public List<Medicament> listerMedicaments() throws SQLException {
        String query = "SELECT * FROM medicament";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Medicament> medicaments = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String description = resultSet.getString("description");
            double prix = resultSet.getDouble("prix");
            int quantite = resultSet.getInt("quantite");
            Medicament medicament = new Medicament(id, nom, description, prix, quantite);
            medicaments.add(medicament);
        }
        return medicaments;
    }


    public Medicament trouverMedicamentParId(int id) throws SQLException {
        String query = "SELECT * FROM medicament WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String description = resultSet.getString("description");
            double prix = resultSet.getDouble("prix");
            int quantite = resultSet.getInt("quantite");
            return new Medicament(id, nom, description, prix, quantite);
        }
        return null;
    }

    public static List<Medicament> filterMedicaments(List<Medicament> allMedicaments, String recherche) {
        if (recherche == null || recherche.trim().isEmpty()) {
            // Si la chaîne de recherche est vide ou nulle, retourner la liste complète des médicaments
            return allMedicaments;
        }
        List<Medicament> filteredMedicaments = new ArrayList<>();
        for (Medicament medicament : allMedicaments) {
            try {
                int id = Integer.parseInt(recherche);
                if (medicament.getId() == id) {
                    filteredMedicaments.add(medicament);
                    continue;
                }
            } catch (NumberFormatException e) {
                // Ignorer l'exception et continuer la boucle
            }
            // Rechercher si la chaîne de recherche est contenue dans le nom, la description, le prix, ou la quantité du médicament
            if (medicament.getNom().toLowerCase().contains(recherche.toLowerCase())
                    || medicament.getDescription().toLowerCase().contains(recherche.toLowerCase())
                    || String.valueOf(medicament.getPrix()).contains(recherche)
                    || String.valueOf(medicament.getQuantite()).contains(recherche)) {
                filteredMedicaments.add(medicament);
            }
        }
        return filteredMedicaments;
    }




}